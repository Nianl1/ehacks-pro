package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntity;

public class PlayerSpotter extends Module {
    public PlayerSpotter() {
        super(ModuleCategory.PACKETLOGGERS);
    }

    @Override
    public String getName() {
        return "PlayerSpotter";
    }

    @Override
    public String getDescription() {
        return "Reads all incoming packets and tries to detect player data.";
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

        if (!(packet instanceof SPacketEntity)) {
            return true;
        }

        Entity entity = ((SPacketEntity) packet).getEntity(Minecraft.getMinecraft().world);

        if (!(entity instanceof EntityPlayer)) {
            return true;
        }

        InteropUtils.log(String.format("Player: %s, packet: %s", ((EntityPlayer) entity).getDisplayNameString(), packet.getClass().getSimpleName()), "PlayerSpotter");

        return true;
    }
}