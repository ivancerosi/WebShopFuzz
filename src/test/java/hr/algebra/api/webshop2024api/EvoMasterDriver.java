package hr.algebra.api.webshop2024api;

import org.evomaster.client.java.controller.InstrumentedSutStarter;

import org.evomaster.client.java.controller.api.dto.auth.AuthenticationDto;
import org.evomaster.client.java.controller.api.dto.database.schema.DatabaseType;
import org.evomaster.client.java.controller.api.dto.SutInfoDto;
import org.evomaster.client.java.controller.internal.SutController;
import org.evomaster.client.java.controller.problem.ProblemInfo;
import org.evomaster.client.java.controller.problem.RestProblem;
import org.evomaster.client.java.sql.DbCleaner;
import org.evomaster.client.java.sql.DbSpecification;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EvoMasterDriver extends org.evomaster.client.java.controller.EmbeddedSutController {
    static List<String> DB_TABLES_SKIP = Arrays.asList(
            // Add tables that shouldn't be cleared between test runs
            "users"
    );

    static String PREFIXES_TO_COVER = "hr.algebra";  // set to hr.algebra to cover only non-library code
                                            // - might have negative impact on code exploration
    static String BASE_URL = "http://localhost:8081";

    static String DB_USER = "sa";
    static String DB_PASS = "springstudent";

    static int SUT_CONTROLLER_PORT = 40100;

    private Connection sqlConnection;

    private List<DbSpecification> dbSpecification;


    private ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        SutController controller = new EvoMasterDriver();
        InstrumentedSutStarter starter = new InstrumentedSutStarter(controller);

        starter.start();
    }

    public EvoMasterDriver() {
        setControllerPort(SUT_CONTROLLER_PORT);
    }

    @Override
    public boolean isSutRunning() {
        return ctx!=null && ctx.isRunning();
    }

    @Override
    public String getPackagePrefixesToCover() {
        return PREFIXES_TO_COVER;
    }

    @Override
    public List<AuthenticationDto> getInfoForAuthentication() {
        return null;
    }

    @Override
    public ProblemInfo getProblemInfo() {
        return new RestProblem(
                BASE_URL+"/api-docs",
                Arrays.asList("/health", "/health.json", "/error")
        );
    }

    @Override
    public SutInfoDto.OutputFormat getPreferredOutputFormat() {
        return SutInfoDto.OutputFormat.JAVA_JUNIT_4;
    }

    @Override
    public String startSut() {
        String port = BASE_URL.split(":")[BASE_URL.split(":").length-1];

        ctx = SpringApplication.run(Webshop2024apiApplication.class, new String[] {
                "--server.port="+port,
                "--spring.datasource.url=jdbc:h2:mem:shoppingsite;DB_CLOSE_DELAY=-1;",
                "--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
                "--spring.datasource.username="+DB_USER,
                "--spring.datasource.password="+DB_PASS
        });

        ctx.addApplicationListener(new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent e) {
                Thread.currentThread().interrupt();
            }
        });

        if (sqlConnection != null) {
            try {
                sqlConnection.close();
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }

        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);

        try {
            sqlConnection = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dbSpecification = Arrays.asList(new DbSpecification(DatabaseType.H2, sqlConnection));

        return BASE_URL;
    }

    @Override
    public void stopSut() {
        ctx.stop();
        SpringApplication.exit(ctx);
    }

    @Override
    public void resetStateOfSUT() {
        DbCleaner.clearDatabase_H2(sqlConnection, DB_TABLES_SKIP);
    }

    @Override
    public List<DbSpecification> getDbSpecifications() {
        return dbSpecification;
    }
}
