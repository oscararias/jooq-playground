package io.trabe.jooqplayground;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trabe.jooqplayground.generated.public_.tables.daos.AuthorDao;

@Configuration
public class JooqConfig {

    //Use this config when not using spring boot starter

//    private final DataSource dataSource;
//
//    private String sqlDialectName = "H2";
//
//    public JooqConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public DataSourceConnectionProvider connectionProvider() {
//        return new DataSourceConnectionProvider(dataSource);
//    }
//
//    @Bean
//    public ExceptionTranslator exceptionTransformer() {
//        return new ExceptionTranslator();
//    }
//
//    @Bean
//    public DefaultDSLContext dsl() {
//        return new DefaultDSLContext(configuration());
//    }
//
//
//    @Bean
//    public DefaultConfiguration configuration() {
//        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
//        jooqConfiguration.set(connectionProvider());
//        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
//        jooqConfiguration.set(SQLDialect.valueOf(sqlDialectName));
//
//        return jooqConfiguration;
//    }
//
//
//    public class ExceptionTranslator extends DefaultExecuteListener {
//        public void exception(ExecuteContext context) {
//            SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(sqlDialectName);
//            context.exception(translator
//                    .translate("Access database using Jooq", context.sql(), context.sqlException()));
//        }
//    }

    @Bean
    public AuthorDao authorDao(org.jooq.Configuration configuration) {
        return new AuthorDao(configuration);
    }

}
