package com.maple.maple_boss_now.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CharacterEquipInfoResponse {
    private String date;
    @JsonProperty("character_gender")
    private String characterGender;
    @JsonProperty("character_class")
    private String characterClass;
    @JsonProperty("preset_no")
    private int presetNo;
    @JsonProperty("item_equipment")
    private List<ItemEquipment> itemEquipment;
    @JsonProperty("item_equipment_preset_1")
    private List<ItemEquipment> itemEquipmentPreset1;
    @JsonProperty("item_equipment_preset_2")
    private List<ItemEquipment> itemEquipmentPreset2;
    @JsonProperty("item_equipment_preset_3")
    private List<ItemEquipment> itemEquipmentPreset3;
    private Title title;
    @JsonProperty("dragon_equipment")
    private List<DragonEquipment> dragonEquipment;
    @JsonProperty("mechanic_equipment")
    private List<MechanicEquipment> mechanicEquipment;

    @Data
    @NoArgsConstructor
    public static class ItemEquipment {
        @JsonProperty("item_equipment_part")
        private String itemEquipmentPart;
        @JsonProperty("item_equipment_slot")
        private String itemEquipmentSlot;
        @JsonProperty("item_name")
        private String itemName;
        @JsonProperty("item_icon")
        private String itemIcon;
        @JsonProperty("item_description")
        private String itemDescription;
        @JsonProperty("item_shape_name")
        private String itemShapeName;
        @JsonProperty("item_shape_icon")
        private String itemShapeIcon;
        @JsonProperty("item_gender")
        private String itemGender;
        @JsonProperty("item_total_option")
        private ItemOption itemTotalOption;
        @JsonProperty("item_base_option")
        private ItemOption itemBaseOption;
        @JsonProperty("potential_option_grade")
        private String potentialOptionGrade;
        @JsonProperty("additional_potential_option_grade")
        private String additionalPotentialOptionGrade;
        @JsonProperty("potential_option_1")
        private String potentialOption1;
        @JsonProperty("potential_option_2")
        private String potentialOption2;
        @JsonProperty("potential_option_3")
        private String potentialOption3;
        @JsonProperty("additional_potential_option_1")
        private String additionalPotentialOption1;
        @JsonProperty("additional_potential_option_2")
        private String additionalPotentialOption2;
        @JsonProperty("additional_potential_option_3")
        private String additionalPotentialOption3;
        @JsonProperty("equipment_level_increase")
        private int equipmentLevelIncrease;
        @JsonProperty("item_exceptional_option")
        private ItemOption itemExceptionalOption;
        @JsonProperty("item_add_option")
        private ItemOption itemAddOption;
        @JsonProperty("growth_exp")
        private int growthExp;
        @JsonProperty("growth_level")
        private int growthLevel;
        @JsonProperty("scroll_upgrade")
        private String scrollUpgrade;
        @JsonProperty("cuttable_count")
        private String cuttableCount;
        @JsonProperty("golden_hammer_flag")
        private String goldenHammerFlag;
        @JsonProperty("scroll_resilience_count")
        private String scrollResilienceCount;
        @JsonProperty("scroll_upgradable_count")
        private String scrollUpgradableCount;
        @JsonProperty("soul_name")
        private String soulName;
        @JsonProperty("soul_option")
        private String soulOption;
        @JsonProperty("item_etc_option")
        private ItemOption itemEtcOption;
        @JsonProperty("starforce")
        private String starforce;
        @JsonProperty("starforce_scroll_flag")
        private String starforceScrollFlag;
        @JsonProperty("item_starforce_option")
        private ItemOption itemStarforceOption;
        @JsonProperty("special_ring_level")
        private int specialRingLevel;
        @JsonProperty("date_expire")
        private String dateExpire;
    }

    @Data
    @NoArgsConstructor
    public static class ItemOption {
        private String str;
        private String dex;
        @JsonProperty("int")
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
    public static class Title {
        @JsonProperty("title_name")
        private String titleName;
        @JsonProperty("title_icon")
        private String titleIcon;
        @JsonProperty("title_description")
        private String titleDescription;
        @JsonProperty("date_expire")
        private String dateExpire;
        @JsonProperty("date_option_expire")
        private String dateOptionExpire;
    }

    @Data
    @NoArgsConstructor
    public static class DragonEquipment extends ItemEquipment {
        // Inherits all fields from ItemEquipment
    }

    @Data
    @NoArgsConstructor
    public static class MechanicEquipment extends ItemEquipment {
        // Inherits all fields from ItemEquipment
    }
}
