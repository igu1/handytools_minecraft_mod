package me.ez.handytools;

import net.neoforged.neoforge.common.ModConfigSpec;

/** Common configuration for Handy Tools. */
public class Config {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ENABLED;

    public static final ModConfigSpec.BooleanValue PATH_ROLLER_ENABLED;
    public static final ModConfigSpec.IntValue PATH_ROLLER_WIDTH;
    public static final ModConfigSpec.IntValue PATH_ROLLER_DEPTH;

    public static final ModConfigSpec.BooleanValue SIGNAL_METER_ENABLED;

    public static final ModConfigSpec.BooleanValue FILTER_HOPPER_ENABLED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Master switch for Handy Tools").translation("handytools.configuration.general").push("general");
        ENABLED = builder.comment("Enable Handy Tools").translation("handytools.configuration.general.enabled")
                .define("enabled", true);
        builder.pop();

        builder.comment("Path Roller").translation("handytools.configuration.path_roller").push("path_roller");
        PATH_ROLLER_ENABLED = builder.comment("Enable the Path Roller")
                .translation("handytools.configuration.path_roller.enabled").define("enabled", true);
        PATH_ROLLER_WIDTH = builder.comment("Half-width of the rolled plane")
                .translation("handytools.configuration.path_roller.width").defineInRange("width", 1, 0, 6);
        PATH_ROLLER_DEPTH = builder.comment("Half-depth of the rolled plane")
                .translation("handytools.configuration.path_roller.depth").defineInRange("depth", 2, 0, 6);
        builder.pop();

        builder.comment("Signal Meter").translation("handytools.configuration.signal_meter").push("signal_meter");
        SIGNAL_METER_ENABLED = builder.comment("Enable the Signal Meter")
                .translation("handytools.configuration.signal_meter.enabled").define("enabled", true);
        builder.pop();

        builder.comment("Filter Hopper").translation("handytools.configuration.filter_hopper").push("filter_hopper");
        FILTER_HOPPER_ENABLED = builder.comment("Enable the Filter Hopper")
                .translation("handytools.configuration.filter_hopper.enabled").define("enabled", true);
        builder.pop();

        SPEC = builder.build();
    }
}
