package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class MinePacketLogIN extends Module {
    public MinePacketLogIN() {
        super(ModuleCategory.PACKETLOGGERS);
    }

    @Override
    public String getName() {
        return "MinePacketLogIN";
    }

    @Override
    public String getDescription() {
        return "Sends info about incoming packets.";
    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public String getModName() {
        return "Minecraft";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (side != PacketHandler.Side.IN) {
            return true;
        }

        if ((packet instanceof SPacketKeepAlive) || (packet instanceof SPacketTimeUpdate) || (packet instanceof SPacketEntityProperties) || (packet instanceof SPacketUpdateHealth)) {
            return true;
        }

        if (!(packet instanceof FMLProxyPacket)) {
            InteropUtils.log(packet.getClass().getName(), "Packet" + side.toString());
        }
        return true;
    }
}