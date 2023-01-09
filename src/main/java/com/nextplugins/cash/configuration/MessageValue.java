package com.nextplugins.cash.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("messages")
@ConfigFile("messages.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageValue implements ConfigurationInjectable {

    @Getter
    private static final MessageValue instance = new MessageValue();

    // requests

    @ConfigField("requests.invalid-amount")
    private String invalidAmount;

    @ConfigField("requests.invalid-target")
    private String invalidTarget;

    @ConfigField("requests.updating-ranking")
    private String updatingRanking;

    // operations

    @ConfigField("operations.set")
    private String setAmount;

    @ConfigField("operations.add")
    private String addAmount;

    @ConfigField("operations.remove")
    private String removeAmount;

    @ConfigField("operations.reset")
    private String resetBalance;

    @ConfigField("operations.see")
    private String seeBalance;

    @ConfigField("operations.see-other")
    private String seeOtherBalance;

    @ConfigField("operations.toggle-on")
    private String toggleOn;

    @ConfigField("operations.toggle-off")
    private String toggleOff;

    // transactions

    @ConfigField("transactions.paid")
    private String paid;

    @ConfigField("transactions.received")
    private String received;

    @ConfigField("transactions.is-yourself")
    private String isYourself;

    @ConfigField("transactions.insufficient-amount")
    private String insufficientAmount;

    @ConfigField("transactions.toggled-off")
    private String toggledOff;

    // convert

    @ConfigField("covert.start")
    private String convertStart;

    @ConfigField("convert.kick")
    private List<String> convertWhitelistKick;

    @ConfigField("convert.end")
    private String convertEnd;

    // npc ranking

    @ConfigField("npc-ranking.wrong-position")
    private String wrongPosition;

    @ConfigField("npc-ranking.position-already-defined")
    private String positionAlreadyDefined;

    @ConfigField("npc-ranking.position-successful-created")
    private String positionSuccessfulCreated;

    @ConfigField("npc-ranking.position-not-yet-defined")
    private String positionNotYetDefined;

    @ConfigField("npc-ranking.position-successful-removed")
    private String positionSuccessfulRemoved;

    @ConfigField("npc-ranking.position-reached-limit")
    private String positionReachedLimit;

    @ConfigField("npc-ranking.npc-help")
    private List<String> npcHelp;

    // commands

    @ConfigField("commands.incorrect-target")
    private String incorrectTarget;

    @ConfigField("commands.incorrect-usage")
    private String incorrectUsage;

    @ConfigField("commands.error")
    private String error;

    @ConfigField("commands.no-permission")
    private String noPermission;

    @ConfigField("commands.help-command")
    private List<String> helpCommand;

    @ConfigField("commands.help-command-staff")
    private List<String> helpCommandStaff;

    @ConfigField("commands.check-help-command")
    private List<String> checkHelpCommand;

    // check system

    @ConfigField("checks.created")
    private String checkCreated;

    @ConfigField("checks.insufficient-value")
    private String checkInsufficientValue;

    @ConfigField("checks.min-value")
    private String checkMinimumValue;

    @ConfigField("checks.used")
    private String checkUsed;

    @ConfigField("checks.received")
    private String checkReceived;

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }
}
