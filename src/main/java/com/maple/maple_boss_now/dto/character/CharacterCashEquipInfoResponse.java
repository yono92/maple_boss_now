package com.maple.maple_boss_now.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CharacterCashEquipInfoResponse {
    private String date;
    @JsonProperty("character_gender")
    private String characterGender;
    @JsonProperty("character_class")
    private String characterClass;
    @JsonProperty("preset_no")
    private int presetNo;

    @JsonProperty("cash_item_equipment_base")
    private List<CashItemEquipment> cashItemEquipmentBase;
    @JsonProperty("cash_item_equipment_preset_1")
    private List<CashItemEquipment> cashItemEquipmentPreset1;
    @JsonProperty("cash_item_equipment_preset_2")
    private List<CashItemEquipment> cashItemEquipmentPreset2;
    @JsonProperty("cash_item_equipment_preset_3")
    private List<CashItemEquipment> cashItemEquipmentPreset3;

    @JsonProperty("additional_cash_item_equipment_base")
    private List<CashItemEquipment> additionalCashItemEquipmentBase;
    @JsonProperty("additional_cash_item_equipment_preset_1")
    private List<CashItemEquipment> additionalCashItemEquipmentPreset1;
    @JsonProperty("additional_cash_item_equipment_preset_2")
    private List<CashItemEquipment> additionalCashItemEquipmentPreset2;
    @JsonProperty("additional_cash_item_equipment_preset_3")
    private List<CashItemEquipment> additionalCashItemEquipmentPreset3;

    @Data
    @NoArgsConstructor
    public static class CashItemEquipment {
        @JsonProperty("cash_item_equipment_part")
        private String cashItemEquipmentPart;
        @JsonProperty("cash_item_equipment_slot")
        private String cashItemEquipmentSlot;
        @JsonProperty("cash_item_name")
        private String cashItemName;
        @JsonProperty("cash_item_icon")
        private String cashItemIcon;
        @JsonProperty("cash_item_description")
        private String cashItemDescription;
        @JsonProperty("cash_item_option")
        private List<CashItemOption> cashItemOption;
        @JsonProperty("date_expire")
        private String dateExpire;
        @JsonProperty("date_option_expire")
        private String dateOptionExpire;
        @JsonProperty("cash_item_label")
        private String cashItemLabel;
        @JsonProperty("cash_item_coloring_prism")
        private CashItemColoringPrism cashItemColoringPrism;
        @JsonProperty("item_gender")
        private String itemGender;
    }

    @Data
    @NoArgsConstructor
    public static class CashItemOption {
        private String str;
        private String dex;
        private String intStat;
        private String luk;
        private String maxHp;
        private String maxMp;
        private String attackPower;
        private String magicPower;
        private String armor;
        private String speed;
        private String jump;
        private String bossDamage;
        private String ignoreMonsterArmor;
        private String allStat;
        private String damage;
        private int equipmentLevelDecrease;
        private String maxHpRate;
        private String maxMpRate;
    }

    @Data
    @NoArgsConstructor
    public static class CashItemColoringPrism {
        @JsonProperty("color_range")
        private String colorRange;
        private int hue;
        private int saturation;
        private int value;
    }
}
