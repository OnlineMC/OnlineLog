package cn.onlinemc.onlinelog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.bukkit.plugin.java.JavaPlugin;

public class TimeCycle extends TimerTask {

	private JavaPlugin p;

	public TimeCycle(JavaPlugin p) {
		this.p = p;
	}

	@Override
	public void run() {
		int onlinePlayer = p.getServer().getOnlinePlayers().length;
		if(Config.echoOnlineLog){
			if(onlinePlayer != 0 && !Config.continueLog){
				String log = "在线玩家 " + onlinePlayer + " 名";
				p.getLogger().info(log);
			}
		}
		if(onlinePlayer == 0 && Config.continueSave){
			return;
		}
		Date date = Config.getNowDate();
		try {

			String logs = Config.formatTime.format(date) + "|" + onlinePlayer;
			List<String> list = Config.loadLog();
			if(list == null){
				list = new ArrayList<String>();
			}
			list.add(logs);

			if (!Config.saveLog(list)) {
				p.getLogger().info("保存配置文件失败");
			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
