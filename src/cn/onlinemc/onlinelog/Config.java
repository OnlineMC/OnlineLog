package cn.onlinemc.onlinelog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cn.onlinemc.onlinelog.config.YamlConfig;

public class Config {

	static JavaPlugin p;
	static int time;
	// static Date date;
	static boolean echoOnlineLog;
	static boolean continueLog;
	static boolean continueSave;

	static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

	public static void inid(JavaPlugin p) {
		Config.p = p;
		int minute = p.getConfig().getInt("time", 10);
		// 获取插件启用时的日期
		// date = getNowDate();
		time = (minute * 60000);
		echoOnlineLog = p.getConfig().getBoolean("echoOnlineLog");
		String version = p.getConfig().getString("version", null);
		if (version == null) {
			p.getLogger().info("发现旧版本的配置 正在更新");
			File file = new File(p.getDataFolder() + "/config.yml");

			if (!file.delete()) {
				p.getLogger().info("移除旧版本配置 失败");

			} else {
				p.saveDefaultConfig();
				p.getConfig().set("time", minute);
				p.getConfig().set("echoOnlineLog", echoOnlineLog);
			}

		}
		continueLog = p.getConfig().getBoolean("continueLog");
		continueSave = p.getConfig().getBoolean("continueSave");
		p.getLogger().info("采样时间间隔:" + minute + "分钟");
		p.getLogger().info("无人在线时 停止输出日志:" + continueLog + " 停止保存日志:" + continueSave);
	}

	// 获取当前日期方法
	public static Date getNowDate() {
		return new Date();
	}

	// 获得配置文件的File
	public static YamlConfig getConfigFile() throws IOException, InvalidConfigurationException {
		String date = formatDate.format(Config.getNowDate());
		File file = new File(p.getDataFolder() + "/log/");
		if (!file.exists()) {
			p.getLogger().info(file + " 不存在");
			if (file.mkdirs()) {
				p.getLogger().info("创建 " + file + " 成功");
			} else {
				p.getLogger().info("创建 " + file + " 失败");
				return null;
			}
		}
		file = new File(p.getDataFolder() + "/log/" + date + ".yml");
		if (!file.exists()) {
			file.createNewFile();
			if (file.exists()) {
				p.getLogger().info("创建新文件 " + date + ".yml 成功");
			} else {
				p.getLogger().info("创建新文件 " + date + ".yml 失败!");
				return null;
			}
		}

		YamlConfig config = new YamlConfig(YamlConfiguration.loadConfiguration(file), file);

		return config;
	}

	// 载入当日的配置文件
	public static List<String> loadLog() throws Exception {
		YamlConfig yaml = getConfigFile();
		FileConfiguration config = yaml.getConfig();
		if (config == null) {
			return null;
		}

		List<?> list = config.getList("logs");
		return (List<String>) list;
	}

	public static List<String> loadLog(File file) throws Exception {
		if (!file.exists()) {
			return null;
		}
		YamlConfig yaml = new YamlConfig(YamlConfiguration.loadConfiguration(file), file);
		FileConfiguration config = yaml.getConfig();
		if (config == null) {
			return null;
		}

		List<?> list = config.getList("logs");
		return (List<String>) list;
	}

	public static boolean saveLog(List<String> list) throws Exception {
		YamlConfig config = getConfigFile();
		if (config == null) {
			return false;
		}
		config.getConfig().set("logs", list);
		config.getConfig().save(config.getFile());
		return true;
	}

}
