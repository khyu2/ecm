package project.ecm.global.logging;

import lombok.RequiredArgsConstructor;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApiQueryInspector implements StatementInspector {

    private final ApiQueryCounter queryCounter;

    @Override
    public String inspect(final String sql) {
        if (isInRequestScope()) {
            queryCounter.increase();
        }
        return sql;
    }

    private boolean isInRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
