# LeaderboardLib
LeaderboardLib is a simple Leaderboard library for Minecraft plugin developers looking to add leaderboards to their plugins and server. (Native version: 1.18)


### First Steps:


#### 1) Installation: 
LeaderboardLib does not have a Maven repository currently, so the only way you can use LeaderboardLib in your plugin is by adding it as a dependency manually. To do this in Intellij, simply locate the "Libraries" tab in your project's "Project Structure", click the green plus sign, and add the LeaderboardLib jar file. LeaderboardLib depends on HolographicDisplays to work! Download the plugin [here](https://dev.bukkit.org/projects/holographic-displays/files) and add it to your server plugins folder

#### 2) Checking for LeaderboardLib
Secondly, we need to check if the plugin is provided upon server startup. To do this, place the following conditional into your onEnable. (Or move it into its own function. It doesn't really matter, as long as it is called in the onEnable block)
~~~java
if (!Bukkit.getPluginManager().isPluginEnabled("LeaderboardLib")) {
            getLogger().severe("*** LeaderboardLib is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
~~~

You're all set! Now you have successfully added LeaderboardLib as a valid dependency


### Library Tutorial Coming Soon
For now, view the source code
