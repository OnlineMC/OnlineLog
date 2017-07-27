package cn.onlinemc.onlinelog.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class YamlConfig {

	private File file;
	private FileConfiguration config;

	public YamlConfig() {
	}

	public YamlConfig( FileConfiguration config,File file) {
		this.config = config;
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}
	

}
