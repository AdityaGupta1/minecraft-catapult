[[Download]]
== Download and Install

=== Download

Download the following software

. **JDK:** Download and install JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
. **Eclipse:** Download Eclipse from http://www.eclipse.org/downloads/eclipse-packages/, pick "`Eclipse IDE for Java Developers`" and unzip.
. **Forge:** Download Forge 1.7.10 from
  http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-src.zip

=== Mac or Windows

Follow the instructions for Mac or Windows as explained below.

==== Mac

Make sure all the software lised in <<Download>> is downloaded.

. In Finder, go to Applications, Utilities, double click on Terminal
. Change directory to Desktop as `cd Desktop`
. Make a new directory by giving the command `mkdir forge`
. Change to the directory `cd forge`
. Unzip the downloaded Forge 1.7.10 zip file as `unzip ~/Downloads/forge-1.7.10-10.13.4.1558-1.7.10-src.zip`
. Give the command `./gradlew setupDecompWorkspace eclipse`. The output should show `BUILD SUCCESSFUL`.

==== Windows

Make sure all the software lised in <<Download>> is downloaded.

. Open a Command Prompt as explained at http://windows.microsoft.com/en-us/windows-vista/open-a-command-prompt-window
. Change directory to Desktop as `cd Desktop`
. Make a new directory by giving the command `mkdir forge`
. Change to the directory `cd forge`
. In File Explorer, double click on the Forge 1.7.10 zip file downloaded earlier and extract all the contents in this newly created directory.
. In Command Prompt, give the command `gradlew setupDecompWorkspace eclipse`. The output should show `BUILD SUCCESSFUL`.

A detailed video with complete installation steps on Mac is given below:

video::0F7Bhswtd_w[youtube]

Lastly, replace the `src` folder in the `forge-1.7.10-10.13.4.1558-1.7.10-src` folder with the `src` folder from this repository.

=== Verify

This is a very important step as this will confirm that you can actually start modding.

. Open up Eclipse
. In the "`Eclipse Launcher`" window, click on `Browse...`, choose the location of `eclipse` directory in the unzipped forge directory
. Click on "`OK`"
. In the Eclipse window, you should see a "`Minecraft`" folder and be able to expand it. There should be no red exclamation marks next to it.

== Using the Catapult

. Click on the green play button at the top of Eclipse to launch Minecraft
. In the game, make sure to create a world in `Creative Mode`
. In the game, open your inventory by pressing `E`
. Click on the compass at the top-right to enter search mode
. Search for `Spawn Catapult`
. Press `T` to open your chat window
. Right click on the ground while holding the `Spawn Catapult` to create a catapult
. Use the command `/catapult <angle> <power> <color>` to set the angle, power, and color of the projectile to be launched
. Right click on the catapult to launch a projectile

== Workshop content

=== Introduction

The aim of the workshop is to introduce Minecraft modding, basic Java principles and - it goes without saying - bring destructions with exploding cows.

The `master` branch contains all the functioning code, the `workshop` branch is the starting point for this workshop

=== Steps

. Introduction
. Spawning the catapult
. Trajectories
. First shot
. Grounding the projectile
. Explosions
. Better Explosions
. Huge Explosions

=== Introduction

We Introduce
- Java
- Minecraft modding
- Eclipse IDE
- Free fall
- CERN (volunteers to give details explanation about the standard model and the Higgs boson  )

=== Spawning the catapult
Let's start Minecraft by clicking on the green arrow and create a new world in **Creative Mode**
When we open the inventory (press `E`) and search for catapult we cannot find anything. We need to register the new catapult entity in the main modding file.

In `Main.java`

```
@EventHandler
public void init(FMLInitializationEvent event)
{
   registerColors();
   registerModEntity(EntityCatapult.class, new RenderCatapult(),
     "catapult", EntityRegistry.findGlobalUniqueEntityId(),
     0xC38751, 0xDCA556);
}
```

now that the catapult is registered, we can restart Minecraft and repeat the operation. We can now find the egg for the catapult. Let's move it in the inventory, equip it, right click and hop we have a catapult!

=== Trajectories

Now that have a catapult we can right click on it to show a trajectory. But we get a error message that the catapult is not setup yet. Let's try that command -> Nothing happens. We need to register it.

Explain

- Initial angle
- Initial speed
- Azimuth

now we can register the `CommandCatapult` that allows to set those parameters:

In `Main.java`

```
@EventHandler
public void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandCatapult());
}
```

Now we can do `/catapult 40 10 red 0` and when we right click on the catapult we see a new trajectory

Let the kid play with different angles and velocity. Make them change the colour when changing settings so they can show different trajectory at the same time.

For a given power, what is the best angle (i.e. the one which gives the best range)?

=== First shot

Catapult are for trowing, so let's throw something. A cow? Why not?!

We want to use the right click for throwing, so we need to re-assign the command for trajectory. Let's use `SHIFT + Right Click`. `SHIFT` causes the player to sneak, so we can use `player.isSneaking()` to check if the `SHIFT` key is pressed:

In `EntityCatapult.java`

```
public boolean interact(EntityPlayer player) {

    [...]

	if (player.isSneaking()) {
		if (trajectories
				.contains(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle))) {
			player.addChatComponentMessage(
					Main.createChatMessage("This trajectory is already being shown!", EnumChatFormatting.RED));
			return false;
		}

		trajectories.add(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle));
		player.addChatComponentMessage(Main.createChatMessage("Added a trajectory with Angle: " + angle
				+ " degrees, Power: " + Main.shownPower + ", Color: " + Main.color, EnumChatFormatting.AQUA));

		return true;
	}
}
```

Now we can prepare the cow to be thrown:

```
EntityCow cow = new EntityCow(world);
cow.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
```

The cow must ride on a block, so we need to prepare that block too:

```
EntityFallingBlock block = createBlock(false);
cow.mountEntity(block);
```

Then we need to spawn the entities into the Minecraft world:

```
world.spawnEntityInWorld(block);
world.spawnEntityInWorld(cow);
```

We can also show a message to notify that a cow has been thrown:

```
player.addChatComponentMessage(Main.createChatMessage("Launching cow...", EnumChatFormatting.AQUA));
```

Let's not forget to return `true` at the end of the method. The method should be

```
public boolean interact(EntityPlayer player) {
		World world = player.getEntityWorld();

		if (!world.isRemote) {
			return false;
		}

		double angle = Main.angle;
		double power = Main.power;


		if (!Main.parametersSet) {
			player.addChatComponentMessage(Main.createChatMessage(
					"Use " + new CommandCatapult().getCommandUsage(null) + " first!", EnumChatFormatting.RED));
			return false;
		}

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemSword) {
			clearTrajectories();
			player.addChatComponentMessage(Main.createChatMessage("Cleared all trajectories", EnumChatFormatting.AQUA));
			return true;
		}

		if (player.isSneaking()) {
			if (trajectories
					.contains(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle))) {
				player.addChatComponentMessage(
						Main.createChatMessage("This trajectory is already being shown!", EnumChatFormatting.RED));
				return false;
			}

			trajectories.add(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle));
			player.addChatComponentMessage(Main.createChatMessage("Added a trajectory with Angle: " + angle
					+ " degrees, Power: " + Main.shownPower + ", Color: " + Main.color, EnumChatFormatting.AQUA));

			return true;
		}

		player.addChatComponentMessage(Main.createChatMessage("Launching cow...", EnumChatFormatting.AQUA));

		EntityCow cow = new EntityCow(world);
		cow.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);

		EntityFallingBlock block = createBlock(false);

		cow.mountEntity(block);

		world.spawnEntityInWorld(block);
		world.spawnEntityInWorld(cow);

		return true;
	}
```

Now let's try it. We need to reset the catapult after each restart: `/catapult 40 10 red 0`

=== Grounding the projectile

Can anyone notice the problem? When the block hits the ground it continues moving a bit. We need to fix that, it's a bug!

There is already a class called `FallingBlockEventHandler.java`. We can use this class to receive update about the flying projectile. When we detect that the block touches the floor, we can ground it by setting its velocity to 0.

This class will be called for all living entities in the world, so we need to make sure that we only target our flying blocks.

First we start by getting the cow

```
Entity entity = event.entity;
```

This entity could be anything alive in the world. We need to filter out the ones we don't want. Let's check that the entity is riding a block using `entity.isRiding()`:

```
@SubscribeEvent
public void immobilizeFallingBlock(LivingUpdateEvent event) {
	Entity entity = event.entity;

	if (!entity.isRiding()) {
		return;
	}
}
```

ok so now we only have ridding entities. We can then access the entity they are riding:

```
Entity ridingEntity = entity.ridingEntity;
```

To be 100% sure, we need to check that the entity being ridden is a flying block. In the Minecraft this is a `EntityFallingBlock`:

```
if (!(ridingEntity instanceof EntityFallingBlock)) {
  return;
}
```

Now we can finally check if the block has touched the ground:

```
if (!ridingEntity.onGround) {
  return;
}
```

Now if we have passed this moment it means have a ridding entity, ridding a block, that has touched the ground. It's time to ground it!

```
ridingEntity.setVelocity(0, 0, 0);
```

So the whole method looks like:

```
@SubscribeEvent
public void immobilizeFallingBlock(LivingUpdateEvent event) {
	Entity entity = event.entity;

	if (!entity.isRiding()) {
		return;
	}

	Entity ridingEntity = entity.ridingEntity;

	if (!(ridingEntity instanceof EntityFallingBlock)) {
		return;
	}

	if (!ridingEntity.onGround) {
		return;
	}

	ridingEntity.setVelocity(0, 0, 0);
}
```

And finally, we need to register this handler in the `Main.java` file:

```
@EventHandler
public void init(FMLInitializationEvent event) {
	registerColors();
	registerModEntity(EntityCatapult.class, new RenderCatapult(), "catapult",
			EntityRegistry.findGlobalUniqueEntityId(), 0xC38751, 0xDCA556);

	MinecraftForge.EVENT_BUS.register(new FallingBlockEventHandler());
}
```

Now if we restart Minecraft we notice that the projectiles stop on impact. Yay!

=== Explosions

Now what's the point of a catapult if you can't blow up stuff I ask you. Let's get to work.

We need to trigger an explosion when the falling block touches the ground. Wait... We already have a piece of code that checks that. Rather than grounding the block we could start an explosion:

In `FallingBlockEventHandler.java`

```
ridingEntity.worldObj.createExplosion(ridingEntity, ridingEntity.posX, ridingEntity.posY, ridingEntity.posZ, 2, false);
ridingEntity.worldObj.removeEntity(entity);
ridingEntity.worldObj.removeEntity(ridingEntity);
```

The first line starts an explosion on the block, using the `X`, `Y`, `Z` position of the block. We will explain the last parameters `2` and `false` later.

Let's try it this way.

=== Better Explosions

We have some explosions but they are a bit weak, aren't they? They actually don't destroy anything. That's a fail.

Let's go back to the `createExplosion` method. The last parameter tells Minecraft if the explosion should destroy blocks around. Clearly it should so we need to change the `false` to `true`

```
ridingEntity.worldObj.createExplosion(ridingEntity, ridingEntity.posX, ridingEntity.posY, ridingEntity.posZ, 2, true);
```

=== Huge Explosions

Ok that's better, but could be better right? Let's have a final look at the `createExplosion` method. The number parameter before `true/false` is the radius of the explosions. This is the number of blocks around the explosions that will be affected. Try to increase to `4`, `6`, `8` and see what happens :D
