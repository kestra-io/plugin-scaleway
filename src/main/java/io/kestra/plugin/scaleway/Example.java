package io.kestra.plugin.scaleway;

import io.kestra.core.models.annotations.Plugin;
import io.kestra.core.models.property.Property;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.models.tasks.Task;
import io.kestra.core.runners.RunContext;
import org.slf4j.Logger;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(
    title = "Example task for the Scaleway plugin.",
    description = "Placeholder task that reverses a given string. Replace with a real Scaleway task."
)
@Plugin(
    examples = {
        @io.kestra.core.models.annotations.Example(
            title = "Reverse a string.",
            full = true,
            code = """
                id: scaleway_example
                namespace: company.team

                tasks:
                  - id: reverse
                    type: io.kestra.plugin.scaleway.Example
                    format: "Hello Scaleway"
                """
        )
    }
)
public class Example extends Task implements RunnableTask<Example.Output> {
    @Schema(
        title = "The string to reverse.",
        description = "Any Pebble-renderable string expression."
    )
    private Property<String> format;

    @Override
    public Example.Output run(RunContext runContext) throws Exception {
        Logger logger = runContext.logger();

        var render = runContext.render(format).as(String.class).orElse("");
        logger.debug(render);

        return Output.builder()
            .child(new OutputChild(StringUtils.reverse(render)))
            .build();
    }

    @Builder
    @Getter
    public static class Output implements io.kestra.core.models.tasks.Output {
        @Schema(
            title = "The reversed string output.",
            description = "Contains the reversed value of the input string."
        )
        private final OutputChild child;
    }

    @Builder
    @Getter
    public static class OutputChild implements io.kestra.core.models.tasks.Output {
        @Schema(
            title = "The reversed value.",
            description = "The input string reversed character by character."
        )
        private final String value;
    }
}
