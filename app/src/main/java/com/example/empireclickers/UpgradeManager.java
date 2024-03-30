package com.example.empireclickers;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the upgrades within the game.
 * Each upgrade could increase the click value, automate clicks, or enhance business efficiency.
 */
public class UpgradeManager {

    // Example of an upgrade structure, could be a class of its own if more complex
    public static class Upgrade {
        String name;
        int cost;
        int level;
        double multiplier;

        public Upgrade(String name, int cost, double multiplier) {
            this.name = name;
            this.cost = cost;
            this.level = 0;
            this.multiplier = multiplier;
        }

        // Apply the upgrade effect, increase cost, etc.
        public void applyUpgrade() {
            level++;
            cost *= 1.15; // Increase cost by 15% for the next level, for example
        }
    }

    private Map<String, Upgrade> upgrades;
    private int money;

    public UpgradeManager() {
        upgrades = new HashMap<>();
        initializeUpgrades();
    }

    private void initializeUpgrades() {
        // Initialize with some example upgrades
        upgrades.put("Click Enhancer", new Upgrade("Click Enhancer", 100, 2.0));
        upgrades.put("Auto Clicker", new Upgrade("Auto Clicker", 500, 1.0));
        // Add more upgrades as needed
    }

    // Attempt to purchase an upgrade. Returns true if successful, false otherwise.
    public boolean purchaseUpgrade(String upgradeName) {
        Upgrade upgrade = upgrades.get(upgradeName);
        if (upgrade != null && money >= upgrade.cost) {
            money -= upgrade.cost;
            upgrade.applyUpgrade();
            return true;
        }
        return false;
    }

    public Upgrade getUpgrade(String upgradeName) {
        return upgrades.get(upgradeName);
    }


    // Call this method when the money changes to update the internal state
    public void setMoney(int money) {
        this.money = money;
    }

    // Call this to get the total click multiplier from all upgrades
    public double getTotalClickMultiplier() {
        return upgrades.values().stream()
                .mapToDouble(upgrade -> Math.pow(upgrade.multiplier, upgrade.level))
                .reduce(1.0, (a, b) -> a * b);
    }

    // Example usage of UpgradeManager in MainActivity or another part of your game
    /*
    UpgradeManager upgradeManager = new UpgradeManager();
    upgradeManager.setMoney(currentMoney);
    if(upgradeManager.purchaseUpgrade("Click Enhancer")) {
        // Update game state, UI, etc.
    }
    */
}
