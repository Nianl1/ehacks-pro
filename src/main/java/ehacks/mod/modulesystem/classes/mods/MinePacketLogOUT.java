package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class MinePacketLogOUT extends Module {
    public MinePacketLogOUT() {
        super(ModuleCategory.PACKETLOGGERS);
    }

    @Override
    public String getName() {
        return "MinePacketLogOUT";
    }

    @Override
    public String getDescription() {
        return "Sends info about outcoming packets.";
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
        if (side != PacketHandler.Side.OUT) {
            return true;
        }

        if (!(packet instanceof FMLProxyPacket)) {
            InteropUtils.log(packet.getClass().getName(), "Packet" + side.toString());
        }
        return true;
    }
}