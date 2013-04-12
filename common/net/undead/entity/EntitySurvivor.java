package net.undead.entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.undead.DistanceChecker;
import net.undead.UndeadMinecraft;

public class EntitySurvivor extends EntityWithInventory implements IMob {

    public EntitySurvivor(World world, boolean debugMode, String second) {
        super(world);
        moveSpeed = .3F;
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackOnCollide(this, net.undead.entity.EntityNewZombie.class, moveSpeed, false));
        tasks.addTask(2, new EntityAIAttackOnCollide(this, net.minecraft.entity.monster.EntityMob.class, moveSpeed, true));
        tasks.addTask(3, new EntityAIWander(this, moveSpeed));
        tasks.addTask(4, new EntityAIWatchClosest(this, EntityNewZombie.class, 8F));
        tasks.addTask(5, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityNewZombie.class, 16F, 0, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, net.minecraft.entity.monster.EntityMob.class, 16F, 0, false));
        isDebug = debugMode;
        firstName = pickRandomName(1);
        if (second == null) {
            secondName = pickRandomName(2);
        } else {
            secondName = second;
        }
        name = firstName + " " + secondName;
        chatName = "<" + name + ">";
        speedOnGround = 0.1F;
        speedOnGround = 0.02F;
        texture = "/mods/UndeadMinecraft/textures/other/clear.png";
        setSize(0.6F, 1.8F);
        skinTexture = getRandomSkinLocation(1);
        eyeTexture = getRandomSkinLocation(2);
        hairTexture = getRandomSkinLocation(3);
        shirtTexture = getRandomSkinLocation(4);
        pantsTexture = getRandomSkinLocation(5);
        shoesTexture = getRandomSkinLocation(6);
        textureBuffer = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
        setInvSize(36);
        textureInt = -1;
        familyNumber = rand.nextInt(1000000000);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    public EntitySurvivor(World world) {
        this(world, false, null);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        handleMembers();
        talk();
        // attackZombies();
        pickupItems();
        if (shouldFollowPlayer) {
            followPlayer();
            if (friend != null && friend.isSprinting() && hasPath()) {
                setSprinting(true);
            } else {
                setSprinting(false);
            }
        }
    }

    public void followPlayer() {
        EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
        PathEntity pathentity;
        if (player != null) {
            float f = player.getDistanceToEntity(this);
            if (f > 4F && f < 16F) {
                pathentity = worldObj.getPathEntityToEntity(this, player, 16F, true, false, false, true);
            } else {
                pathentity = null;
            }
            getNavigator().setPath(pathentity, moveSpeed);
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {

        if (!isFriends && leader && !(entityToAttack instanceof EntityPlayer)) {
            leaderGreet();
            becomeFriendsPlayer(entityplayer);
        }

        if (!isFriends && follower) {
            followerGreet();
        }
        
        if(isFriends){
            openInventory(entityplayer);
        }

        return true;
    }

    public boolean isItemValuable(Item item) {
        Item valuable[] = { Item.swordWood, Item.swordStone, Item.swordSteel, Item.swordGold, Item.swordGold, Item.swordDiamond, Item.diamond, Item.redstone, Item.bed, Item.cookie, Item.porkCooked, Item.porkRaw, Item.appleGold, Item.chickenCooked, Item.chickenRaw, Item.arrow, Item.bow, Item.axeSteel, Item.axeDiamond, Item.axeGold, Item.bootsGold, Item.bootsSteel, Item.bootsLeather, Item.legsChain, Item.legsDiamond, Item.legsGold, Item.legsSteel, Item.legsLeather, Item.bucketLava, Item.bucketMilk, Item.bucketEmpty, Item.boat, Item.minecartCrate, Item.minecartEmpty, Item.minecartPowered, Item.egg, Item.rottenFlesh, Item.goldNugget, Item.ingotGold, Item.ingotIron, Item.plateChain, Item.plateDiamond, Item.plateGold, Item.plateLeather, Item.plateSteel, UndeadMinecraft.clothes };
        for (Item i : valuable) {
            if (i.itemID == item.itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemValuable(int item) {
        return isItemValuable(Item.itemsList[item]);
    }

    public void pickupItems() {
        @SuppressWarnings("unchecked")
        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(net.minecraft.entity.item.EntityItem.class, boundingBox.expand(16D, 32D, 16D));

        if (items.isEmpty()) {
            return;
        }
        for (EntityItem item : items) {
            if (isItemValuable(item.getEntityItem().itemID)) {
                setPathToEntity(worldObj.getPathEntityToEntity(this, item, 16F, true, false, false, true));
            }
            if (item.getDistanceToEntity(this) < 2F) {
                inventory.addItemStackToInventory(item.getEntityItem());
                worldObj.playSoundAtEntity(this, "random.pop", 1F, 1F);
                item.setDead();
            }
        }
    }

    public void becomeFriendsPlayer(EntityPlayer p) {
        isFriends = true;
        friend = p;
        shouldFollowPlayer = true;
        health = 20;
        setAttackTarget(null);
    }

    public void followerGreet() {
        EntitySurvivor survivor2 = getLeader();
        if (survivor2 != null) {

            String followergreet[] = { "Ermm.. i don't know, ask our leader, his name is " + survivor2.firstName, "Ask our leader, " + survivor2.firstName + "...", "I'm not the one the one to decide, go and ask " + survivor2.firstName + ".", "Don't waste my time! ask our leader, " + survivor2.firstName + ".", "I don't know if i can trust you. I guess its " + survivor2.firstName + "'s choice.", "Hey " + survivor2.firstName + "... Think we can trust this guy?" };

            say(followergreet[rand.nextInt(followergreet.length)]);
        }
    }

    public void leaderGreet() {
        String leadergreet[] = { "Oh, hello, Me and my family will help you out!", "You must have been through hell. Welcome to the family ", "Glad to see another friendly face", "Those things....I've seen what they do. If they catch you..." };
        say(leadergreet[rand.nextInt(leadergreet.length)]);
    }

    @Override
    public boolean getCanSpawnHere() {
        return !worldObj.isAirBlock((int) posX, (int) posY - 1, (int) posZ);
    }

    public String getRandomSkinLocation(int i) {
        int skinColour[] = { 1, 2 };
        int eyeColour[] = { 1, 2, 3 };
        int hairColour[] = { 1, 2 };
        int shirtColour[] = { 1, 2, 3, 4, 5, 6 };
        int pantsColour[] = { 1, 2, 3, 4, 5, 6 };
        int shoesColour[] = { 1, 2, 3, 4, 5 };

        if (i == 1) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/blankChar" + skinColour[rand.nextInt(skinColour.length)] + ".png";
        }
        if (i == 2) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/Eyes/eyes" + eyeColour[rand.nextInt(eyeColour.length)] + ".png";
        }
        if (i == 3) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/Hair/hair" + hairColour[rand.nextInt(hairColour.length)] + ".png";
        }
        if (i == 4) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/Shirts/shirt" + shirtColour[rand.nextInt(shirtColour.length)] + ".png";
        }
        if (i == 5) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/Pants/pants" + pantsColour[rand.nextInt(pantsColour.length)] + ".png";
        }
        if (i == 6) {
            return "/mods/UndeadMinecraft/textures/other/ZombieMod/Survivors/Shoes/shoes" + shoesColour[rand.nextInt(shoesColour.length)] + ".png";
        }
        return null;
    }

    public void log(Object a) {
        System.out.println(a);
    }

    /**
     * This method handles what role each survivor takes in there current family
     * and what each family member should do.
     * 
     * @param none
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void handleMembers() {
        List nearby = worldObj.getEntitiesWithinAABB(EntitySurvivor.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
        if (nearby.isEmpty()) {
            return;
        }
        ArrayList<EntitySurvivor> nearbyFamily = (ArrayList<EntitySurvivor>) nearby;
        Collections.sort(nearbyFamily, new Comparator<EntitySurvivor>() {
            @Override
            public int compare(EntitySurvivor o1, EntitySurvivor o2) {
                return Integer.signum(o1.familyNumber - o2.familyNumber);
            }
        });
        if (nearbyFamily.get(0) == this) {
            leader = true;
            follower = false;
        } else {
            leader = false;
            follower = true;
        }

        if (follower && getLeader() != null) {
            EntitySurvivor survivor = getLeader();
            if (survivor.isFriends && !isFriends) {
                becomeFriendsPlayer(survivor.friend);
            }
            if (survivor.getDistanceToEntity(this) > 4F) {
                getNavigator().setPath(worldObj.getPathEntityToEntity(this, survivor, 16F, true, false, false, true), moveSpeed);
            }
        }
    }

    private int getPotionEffect() {
        if (isPotionActive(Potion.digSpeed)) {
            return 6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1;
        }
        if (isPotionActive(Potion.digSlowdown)) {
            return 6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2;
        } else {
            return 6;
        }
    }

    @Override
    protected void updateEntityActionState() {

        int i2 = getPotionEffect();
        if (isSwinging) {
            swingProgressInt++;
            if (swingProgressInt >= i2) {
                swingProgressInt = 0;
                isSwinging = false;
            }
        } else {
            swingProgressInt = 0;
        }
        swingProgress = (float) swingProgressInt / (float) i2;
        super.updateEntityActionState();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("Friends", isFriends);
        compound.setBoolean("ShouldFollow", shouldFollowPlayer);
        compound.setString("firstname", firstName);
        compound.setString("secondname", secondName);
        compound.setInteger("familynumber", familyNumber);
        compound.setString("Name", name);
        compound.setString("Chat Name", chatName);
        compound.setString("skin", skinTexture);
        compound.setString("eye", eyeTexture);
        compound.setString("hair", hairTexture);
        compound.setString("shirt", shirtTexture);
        compound.setString("pant", pantsTexture);
        compound.setString("shoe", shoesTexture);
        compound.setBoolean("debug", isDebug);
        compound.setBoolean("leader", leader);
        compound.setBoolean("follower", follower);

        super.writeEntityToNBT(compound);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
    }

    @SuppressWarnings("unchecked")
    public void attackZombies() {
        ArrayList<EntityNewZombie> zombies = (ArrayList<EntityNewZombie>) worldObj.getEntitiesWithinAABB(EntityNewZombie.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(32D, 8D, 32D));
        if (zombies.isEmpty()) {
            return;
        }
        Collections.sort(zombies, new DistanceChecker(this));
        setAttackTarget(zombies.get(0));
    }

    public void becomeZombie() {
        EntityNewZombie zombie = new EntityNewZombie(worldObj);
        zombie.setLocationAndAngles(posX, posY, posZ, rotationPitch, rotationYaw);
        worldObj.spawnEntityInWorld(zombie);
        setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        boolean friends = compound.getBoolean("Friends");
        boolean follow = compound.getBoolean("ShouldFollow");
        String myName = compound.getString("Name");
        String chatname = compound.getString("Chat Name");
        String fname = compound.getString("firstname");
        String sname = compound.getString("secondname");
        String skin = compound.getString("skin");
        String eye = compound.getString("eye");
        String hair = compound.getString("hair");
        String shirt = compound.getString("shirt");
        String pant = compound.getString("pant");
        String shoe = compound.getString("shoe");
        boolean debugmode = compound.getBoolean("debug");
        boolean followtemp = compound.getBoolean("follower");
        boolean leadertemp = compound.getBoolean("leader");

        if (name.length() > 0) {
            shouldFollowPlayer = follow;
            isFriends = friends;
            name = myName;
            chatName = chatname;
            firstName = fname;
            secondName = sname;
            skinTexture = skin;
            eyeTexture = eye;
            hairTexture = hair;
            shirtTexture = shirt;
            pantsTexture = pant;
            shoesTexture = shoe;
            isDebug = debugmode;
            leader = leadertemp;
            follower = followtemp;
        }
        super.readEntityFromNBT(compound);
    }

    @SuppressWarnings("rawtypes")
    public EntitySurvivor getLeader() {
        EntitySurvivor survivor2 = null;
        List list = worldObj.getEntitiesWithinAABB(EntitySurvivor.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(32D, 8D, 32D));
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                EntitySurvivor survivor = (EntitySurvivor) list.get(i);
                if (survivor.isFamily(this) && survivor.leader) {
                    survivor2 = survivor;
                    return survivor2;
                }
            }
        }
        return null;
    }

    public boolean isFamily(EntitySurvivor survivor) {
        return survivor.secondName.equalsIgnoreCase(secondName);
    }

    public void say(String speech) {
        ModLoader.getMinecraftInstance().thePlayer.addChatMessage(chatName + " " + speech);
    }

    public String getName(int part) {
        if (part == 1) {
            return firstName;
        }
        if (part == 2) {
            return secondName;
        }
        if (part == 3) {
            return name;
        }
        return null;
    }

    public int calculateAS(Entity entity) {
        if (getHeldItem() != null && getHeldItem().getItem() instanceof ItemSword) {
            return getHeldItem().getDamageVsEntity(entity);
        } else if (getHeldItem() == null) {
            return 2;
        } else {
            return 2;
        }
    }

    protected void attackNormal(Entity entity, float distance, boolean weapon) {
        if (distance < 3.5F && attackTime-- <= 0) {
            attackStrength = calculateAS(entity);
            if (getHeldItem() != null) {
                getHeldItem().damageItem(1, this);
            }
            if (rand.nextBoolean()) {
                jump();
            }
            Minecraft mc = ModLoader.getMinecraftInstance();
            if (!onGround) {
                mc.effectRenderer.addEffect(new EntityCrit2FX(mc.theWorld, entity));
            }
            attackEntityAsMob(entity);
            attackTime = 30;
        }
    }

    protected void attackBow(Entity entity, float distance) {
        if (distance < 15F && attackTime-- <= 0 && inventory.contains(Item.arrow.itemID)) {
            EntityArrow entityarrow = new EntityArrow(worldObj, this, (EntityLiving) entityToAttack, 1.6F, 12F);
            inventory.consumeInventoryItem(Item.arrow.itemID);
            worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            if (distance < 5F) {
                moveEntityWithHeading(0F, -0.5F);
            }
            if (getHeldItem() != null) {
                getHeldItem().damageItem(1, this);
            }
            worldObj.spawnEntityInWorld(entityarrow);
            attackTime = 50;
        } else if (!inventory.contains(Item.arrow.itemID)) {
            attackNormal(entity, distance, false);
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f) {
        if (getHeldItem() == null || Item.itemsList[getHeldItem().itemID] instanceof ItemSword) {
            attackNormal(entity, f, inventory.getStackInSlot(0) != null);
        }
        if (getHeldItem() != null && getHeldItem().itemID == Item.bow.itemID) {
            attackBow(entity, f);
        }
    }

    @Override
    public void swingItem() {
        if (!isSwinging || swingProgressInt >= getPotionEffect() / 2 || swingProgressInt < 0) {
            swingProgressInt = -1;
            isSwinging = true;
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        int i = attackStrength;
        if (isPotionActive(Potion.damageBoost)) {
            i += 3 << getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }
        if (isPotionActive(Potion.weakness)) {
            i -= 2 << getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        swingItem();
        entity.addVelocity(-MathHelper.sin(rotationYaw * 3.141593F / 180F) * 2 * 0.5F, 0.10000000000000001D, MathHelper.cos(rotationYaw * 3.141593F / 180F) * 2 * 0.5F);
        motionX *= 0.59999999999999998D;
        motionZ *= 0.59999999999999998D;
        setSprinting(false);
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i) {
        EntityLiving attackingentity = (EntityLiving) damagesource.getEntity();
        if (attackingentity instanceof EntityPlayer && isFriends || attackingentity instanceof EntitySurvivor && isFamily((EntitySurvivor) attackingentity)) {
            return false;
        }
        setAttackTarget(attackingentity);
        return super.attackEntityFrom(damagesource, i);
    }

    public void talk() {
        Entity attacker = entityToAttack;
        if (attacker == null) {
            shoutTime = 100 + rand.nextInt(500);
        }
        if (attacker instanceof EntityNewZombie) {
            int distance = MathHelper.floor_double(attacker.getDistanceToEntity(this) + 1) / 10;
            String zombieSpeech[] = { "Zombies! about " + distance + " block" + (distance > 1 ? "s " : " ") + " away!", "I can see a walker!", "Roamers, " + distance + " block" + (distance > 1 ? "s " : " ") + "away!" };
            if (shoutTime-- <= 0) {
                say(zombieSpeech[rand.nextInt(zombieSpeech.length)]);
                shoutTime = 1000 + rand.nextInt(2500);
            }
        }
    }

    public String pickRandomName(int part) {
        String namePart1[] = { "Shaun", "Ruben", "Jonathan", "Anten", "Yonathan", "Jack", "Jake", "Simon", "Alex", "Oliver", "Nathan", "Nathaniel", "Charlie", "Michael", "Harry", "Connor", "Scott", "Robert", "Aaron", "Adam", "Lee", "Philip", "Daniel", "John", "Nick", "Samuel", "Luke", "James", "Liam", "Callum", "Eddie", "Joe", "Kieron", "Andie", "Andy", "Anthony", "Stewart", "Benjamin", "Brandon", "Peter", "Rodney", "Cameron", "Derren", "Darren", "Owen", "Bradley", "Curtis", "Darcy", "Daryl", "Declan", "Richard", "Dillon", "Ed", "Edd", "Evan", "Gavin", "Gaz", "Craig", "Jamie", "Chris", "Sebastian", "Joshua", "Albert", "Hayden", "Patrick", "Homer", "Marge", "Lisa", "Bart", "Jim", "Lewis", "Ben", };
        String namePart2[] = { "Lawton", "Pickup", "Mullins", "Wilson", "Williams", "Martin", "Heaney", "Harrison", "Wild", "Richards", "McGrath", "Maguire", "Clayton", "Manning", "Murphy", "Rogers", "Sid", "Ward", "Watkinson", "Ireland", "Crossley", "Lloyd", "Bailey", "Kennedy", "Neilson", "Davies", "Crompton", "Smith", "Brown", "Green", "Ogden", "Jordan", "Roberts", "Walkley", "Drysdale", "Ashworth", "Wesker", "Hicks", "Robinson", "Rogersen", "Denham", "Tomlinson", "Monks", "Large", "Buckley", "Jones", "Jackson", "Bell", "Blaire", "Butterworth", "Cosgrove", "Anderson", "Simpson" };
        if (part == 1) {
            return namePart1[rand.nextInt(namePart1.length)];
        } else if (part == 2) {
            return namePart2[rand.nextInt(namePart2.length)];
        }
        return null;
    }

    public void switchItems(int item1Slot, int item2Slot) {
        if (inventory.getStackInSlot(item1Slot) == null || inventory.getStackInSlot(item2Slot) == null) {
            return;
        }
        ItemStack item1 = inventory.getStackInSlot(item1Slot);
        ItemStack item2 = inventory.getStackInSlot(item2Slot);
        inventory.setInventorySlotContents(item1Slot, item2);
        inventory.setInventorySlotContents(item2Slot, item1);
    }

    @SuppressWarnings("unchecked")
    public boolean hasFamily() {
        List<EntitySurvivor> list = worldObj.getEntitiesWithinAABB(EntitySurvivor.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(32D, 8D, 32D));
        if (list.isEmpty()) {
            for (EntitySurvivor survivor : list) {
                if (survivor.isFamily(this)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public ItemStack getRandomStarter() {
        int i = rand.nextInt(16);

        switch (i) {
            case 1:
                return new ItemStack(Item.swordSteel, 1, rand.nextInt(100));
            case 2:
                return new ItemStack(Item.swordWood, 1, rand.nextInt(100));
            case 3:
                return new ItemStack(Item.swordStone, 1, rand.nextInt(100));
            case 4:
                return new ItemStack(Item.bow, 1, rand.nextInt(100));
            case 5:
                return new ItemStack(Item.axeWood);
            case 6:
                return new ItemStack(Item.axeStone, 1, rand.nextInt(100));
            case 7:
                return new ItemStack(Block.torchWood, rand.nextInt(32));
            case 8:
                return new ItemStack(Item.arrow, rand.nextInt(64));
            case 9:
                return new ItemStack(Block.tnt, rand.nextInt(4));
            case 10:
                if (rand.nextInt(20) == 5) {
                    return new ItemStack(Item.swordDiamond);
                } else {
                    return new ItemStack(Item.swordStone, 1, rand.nextInt(100));
                }
            case 11:
                return new ItemStack(Block.cobblestone, rand.nextInt(64));
            case 12:
                return new ItemStack(Block.sapling, rand.nextInt(16), rand.nextInt(3));
            case 13:
                return new ItemStack(Item.appleRed, rand.nextInt(16));
            case 14:
                return new ItemStack(Item.bucketLava);
            case 15:
                return new ItemStack(Item.bucketEmpty);
            case 16:
                return new ItemStack(Item.bed);

        }
        return null;
    }

    @Override
    public ItemStack getHeldItem() {
        return inventory.getStackInSlot(27);
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public void openInventory(EntityPlayer player) {
       player.openGui(UndeadMinecraft.instance, 100, worldObj, entityId, 0, 0);
        // ModLoader.openGUI(theplayer, new GuiSurvivalInventory(theplayer, this));
    }

    public String        name;
    public String        chatName;
    public boolean       isFriends;
    public boolean       shouldFollowPlayer;
    public boolean       isDebug;
    public boolean       psychopath;
    public boolean       isSprinting;
    protected float      speedOnGround;
    protected float      speedInAir;
    public String        skinTexture;
    public String        eyeTexture;
    public String        hairTexture;
    public String        shirtTexture;
    public String        pantsTexture;
    public String        shoesTexture;
    String               firstName;
    String               secondName;
    public BufferedImage textureBuffer;
    public int           textureInt;
    public int           familyNumber;
    public boolean       leader        = false;
    public boolean       follower      = true;
    public int           swingProgressInt;
    public boolean       isSwinging;
    public int           attackStrength;
    public ItemStack     heldItem;
    public ItemStack     defaultHeldItem;
                                               ModLoader.getMinecraftInstance().thePlayer;    public int           shoutTime;
    public EntityPlayer  friend;
    public int           pickupTime;
    public String        familygreet[] = { "" };

}