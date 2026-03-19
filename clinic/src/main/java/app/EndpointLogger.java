package app;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class EndpointLogger implements ApplicationListener<ApplicationReadyEvent> {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public EndpointLogger(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<RequestMappingInfo, ?> mappings = requestMappingHandlerMapping.getHandlerMethods();

        List<String> lines = new ArrayList<>();
        for (RequestMappingInfo info : mappings.keySet()) {
            Set<String> patterns = info.getPatternValues();
            Set<org.springframework.web.bind.annotation.RequestMethod> methods =
                    info.getMethodsCondition().getMethods();

            String methodStr = methods.isEmpty() ? "ALL" : methods.toString().replace("[", "").replace("]", "");
            for (String pattern : patterns) {
                lines.add(String.format("  [%-6s]  %s", methodStr, pattern));
            }
        }

        lines.sort(Comparator.naturalOrder());

        System.out.println("\n================================================");
        System.out.println("  ENDPOINTS DISPONIBLES");
        System.out.println("================================================");
        lines.forEach(System.out::println);
        System.out.println("================================================\n");
    }
}
