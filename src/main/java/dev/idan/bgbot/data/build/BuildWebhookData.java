package dev.idan.bgbot.data.build;

import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.deployment.DeploymentWebhookData;

@JsonTypeName("build")
public class BuildWebhookData extends DeploymentWebhookData {}
