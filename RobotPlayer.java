package taohao;
import battlecode.common.*;

import java.security.cert.TrustAnchor;
import java.util.Random;
public strictfp class RobotPlayer {
    static int turnCount = 0;
    static final Random rng = new Random(6147);
    static int minernumber=0;
    static int rubbleVision = 0;
    static int leadVision = 0;
    static int robotAge=1;
    static int robotAge2=1;
    static int goldVision = 0;
    static int height;
    static int width;
    static MapLocation archonloc0;
    static MapLocation archonloc1;
    static MapLocation archonloc2;
    static MapLocation archonloc3;
    static MapLocation archonloc4;
    static MapLocation archonloc5;
    static MapLocation archonloc6;
    static MapLocation archonloc7;
    static MapLocation archonloc8;
    static MapLocation archonloc9;
    static final Direction[] directions = 
    {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.CENTER,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };
    @SuppressWarnings("used")
    public static void run(RobotController rc) throws GameActionException 
    {
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());
        rc.setIndicatorString("Hello world!");
        while (true) 
        {
            height=rc.getMapHeight();
            width=rc.getMapWidth();
            turnCount += 1;
            System.out.println("Age: " + turnCount + "; Location: " + rc.getLocation());
            Team ally = rc.getTeam();
            Team opponent = ally.opponent();
            try 
            {
                switch (rc.getType()) 
                {
                    case ARCHON:     runArchon(rc);  break;
                    case MINER:      runMiner(rc);   break;
                    case SOLDIER:    runSoldier(rc); break;
                    case LABORATORY: break;
                    case WATCHTOWER: runWatchtower(rc); break;
                    case BUILDER:    runBuilder(rc); break;
                    case SAGE:       break;
                }
                randomMove(rc);
            }
            catch (GameActionException e) 
            {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } 
            catch (Exception e) 
            {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } 
            finally 
            {
                Clock.yield();
            }
        }
    }
    static void runArchon(RobotController rc) throws GameActionException 
    {
        Direction dir = directions[rng.nextInt(directions.length)];
        int archoncode = rc.getID();
        if (archoncode==0)
        {
            archonloc0 = rc.getLocation();
        }
        else
        {
            archonloc0 = null;
        }
        if (archoncode==1)
        {
            archonloc1 = rc.getLocation();
        }
        else
        {
            archonloc1 = null;
        }
        if (archoncode==2)
        {
            archonloc2 = rc.getLocation();
        }
        else
        {
            archonloc2 = null;
        }
        if (archoncode==3)
        {
            archonloc3 = rc.getLocation();
        }
        else
        {
            archonloc3 = null;
        }
        if (archoncode==4)
        {
            archonloc4 = rc.getLocation();
        }
        else
        {
            archonloc4 = null;
        }
        if (archoncode==5)
        {
            archonloc5 = rc.getLocation();
        }
        else
        {
            archonloc5 = null;
        }
        if (archoncode==6)
        {
            archonloc6 = rc.getLocation();
        }
        else
        {
            archonloc6 = null;
        }
        if (archoncode==7)
        {
            archonloc7 = rc.getLocation();
        }
        else
        {
            archonloc7 = null;
        }
        if (archoncode==8)
        {
            archonloc8 = rc.getLocation();
        }
        else
        {
            archonloc8 = null;
        }
        if (archoncode==9)
        {
            archonloc9 = rc.getLocation();
        }
        else
        {
            archonloc9 = null;
        }
        if (turnCount==0)//turnCount%2==0) 
        {
            if (rc.canBuildRobot(RobotType.MINER, dir)) 
            {
                rc.buildRobot(RobotType.MINER, dir);
            }
            else
            {
                turnCount--;
            }
        } 
        else 
        {
            rc.setIndicatorString("Trying to build a soldier");
            if (rc.canBuildRobot(RobotType.SOLDIER, dir)) 
            {
                rc.buildRobot(RobotType.SOLDIER, dir);
            }
            else
            {
                turnCount--;
            }
        }
    }
    static void runMiner(RobotController rc) throws GameActionException 
    {
        MapLocation me = rc.getLocation();
        int mhp=rc.getHealth();
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        int visiontile = -1;
        int i = 1;
        if (i == 1) 
        { 
            MapLocation archonProducedMeLocation = rc.getLocation();
            i++;
        }
        if (mhp<=0)
        {
            minernumber--;
        }
        for (int dx = -1; dx <= 1; dx++) 
        {
            for (int dy = -1; dy <= 1; dy++) 
            {
                MapLocation mineLocation = new MapLocation(me.x + dx, me.y + dy);
                while (rc.canMineGold(mineLocation)) 
                {
                    rc.mineGold(mineLocation);
                }
            }
        } 
        for (int dx = -1; dx <= 1; dx++) 
        {
            for (int dy = -1; dy <= 1; dy++) 
            {
                MapLocation mineLocation = new MapLocation(me.x + dx, me.y + dy);
                while (rc.canMineLead(mineLocation)) 
                {
                    rc.mineLead(mineLocation);
                }
            }
        }
        for (int dx = -3; dx <= 3; dx++) 
        {
            for (int dy = -3; dy <= 3; dy++) 
            {
                MapLocation visionLocation =  new MapLocation(me.x + dx, me.y + dy);
                Direction mind = rc.getLocation().directionTo(visionLocation);
                if (rc.canSenseLocation(visionLocation)) 
                {
                    visiontile++;
                    rubbleVision = rc.senseRubble(visionLocation);
                    leadVision = rc.senseLead(visionLocation);
                    goldVision = rc.senseGold(visionLocation);
                    if (leadVision!=0)
                    {
                        if (rc.canMove(mind)) 
                        {
                            rc.move(mind);
                        }
                    }
                    if (goldVision!=0)
                    {
                        if (rc.canMove(mind)) 
                        {
                            rc.move(mind);
                        }
                    }
                    if (rc.canSenseRobotAtLocation(visionLocation)) 
                    {
                        if (enemies.length > 0) 
                        {
                            MapLocation toleave = enemies[0].location;
                            Direction canleave = rc.getLocation().directionTo(toleave).opposite();
                            if (rc.canMove(canleave)) 
                            {
                                rc.move(canleave);
                            }
                        }
                    }
                }
            }
        }
        if (turnCount!= 1)
        {
            MapLocation earchon =  new MapLocation((width - archonloc2.x), (height - archonloc2.y));
            Direction enemyarchon = rc.getLocation().directionTo(earchon);
            if (rc.canMove(enemyarchon)) 
            {
                rc.move(enemyarchon);
            }
        }
    }
    static void runSoldier(RobotController rc) throws GameActionException 
    {
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        Direction dir = directions[rng.nextInt(directions.length)];
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length > 0) 
        {
            MapLocation toAttack = enemies[0].location;
            if (rc.canAttack(toAttack)) 
            {
                rc.attack(toAttack);
            }
        }
        if (turnCount!=0)
        {
            while (archonloc0!=null)
            {
                MapLocation earchon0 =  new MapLocation((width - archonloc0.x), (height - archonloc0.y));
                Direction enemyarchon0 = rc.getLocation().directionTo(earchon0);
                if (rc.canMove(enemyarchon0)) 
                {
                    rc.move(enemyarchon0);
                }
            }
            while (archonloc1!=null)
            {
                MapLocation earchon1 =  new MapLocation((width - archonloc1.x), (height - archonloc1.y));
                Direction enemyarchon1 = rc.getLocation().directionTo(earchon1);
                if (rc.canMove(enemyarchon1)) 
                {
                    rc.move(enemyarchon1);
                }
            }
            while (archonloc2!=null)
            {
                MapLocation earchon2 =  new MapLocation((width - archonloc2.x), (height - archonloc2.y));
                Direction enemyarchon2 = rc.getLocation().directionTo(earchon2);
                if (rc.canMove(enemyarchon2)) 
                {
                    rc.move(enemyarchon2);
                }
            }
            while (archonloc3!=null)
            {
                MapLocation earchon3 =  new MapLocation((width - archonloc3.x), (height - archonloc3.y));
                Direction enemyarchon3 = rc.getLocation().directionTo(earchon3);
                if (rc.canMove(enemyarchon3)) 
                {
                    rc.move(enemyarchon3);
                }
            }
            while (archonloc4!=null)
            {
                MapLocation earchon4 =  new MapLocation((width - archonloc4.x), (height - archonloc4.y));
                Direction enemyarchon4 = rc.getLocation().directionTo(earchon4);
                if (rc.canMove(enemyarchon4)) 
                {
                    rc.move(enemyarchon4);
                }
            }
            while (archonloc5!=null)
            {
                MapLocation earchon5 =  new MapLocation((width - archonloc5.x), (height - archonloc5.y));
                Direction enemyarchon5 = rc.getLocation().directionTo(earchon5);
                if (rc.canMove(enemyarchon5)) 
                {
                    rc.move(enemyarchon5);
                }
            }
            while (archonloc6!=null)
            {
                MapLocation earchon6 =  new MapLocation((width - archonloc6.x), (height - archonloc6.y));
                Direction enemyarchon6 = rc.getLocation().directionTo(earchon6);
                if (rc.canMove(enemyarchon6)) 
                {
                    rc.move(enemyarchon6);
                }
            }
            while ((archonloc7!=null)==true)
            {
                MapLocation earchon7 =  new MapLocation((width - archonloc7.x), (height - archonloc7.y));
                Direction enemyarchon7 = rc.getLocation().directionTo(earchon7);
                if (rc.canMove(enemyarchon7)) 
                {
                    rc.move(enemyarchon7);
                }
            }
            while (archonloc8!=null)
            {
                MapLocation earchon8 =  new MapLocation((width - archonloc8.x), (height - archonloc8.y));
                Direction enemyarchon8 = rc.getLocation().directionTo(earchon8);
                if (rc.canMove(enemyarchon8)) 
                {
                    rc.move(enemyarchon8);
                }
            }
            while (archonloc9!=null)
            {
                MapLocation earchon9 =  new MapLocation((width - archonloc9.x), (height - archonloc9.y));
                Direction enemyarchon9 = rc.getLocation().directionTo(earchon9);
                if (rc.canMove(enemyarchon9)) 
                {
                    rc.move(enemyarchon9);
                }
            }
        }
    }
    static void runWatchtower(RobotController rc) throws GameActionException {
        Team ally = rc.getTeam();
        Team opponent = Team.A;
        if (ally == Team.A) {
            opponent = Team.B;
        }
        MapLocation me = rc.getLocation();
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(me, -1, opponent);
        int[] robotvalue = new int[nearbyRobots.length]; // makes targeting value array for each enemy robot
        int rhealth;
        RobotType rtype;
        for (int i = 0; i < nearbyRobots.length; i++) {
            rtype = nearbyRobots[i].type; // gets type of robot

            rhealth = nearbyRobots[i].health; // gets health of robot

            robotvalue[i] = (100 - rhealth); // creating priority valuations
            // going for last hits with priority on higher value units
            if (rtype == RobotType.SAGE) {
                robotvalue[i] = robotvalue[i] + 200;
            }
            if (rtype == RobotType.WATCHTOWER) {
                robotvalue[i] = robotvalue[i] + 150;
            }
            if (rtype == RobotType.ARCHON) {
                robotvalue[i] = robotvalue[i] + 1500;
            }
            if (rtype == RobotType.SOLDIER) {
                robotvalue[i] = robotvalue[i] + 100;
            }
        }
        int maximum = robotvalue[0];   // find maximum robotvalue and index
        int index = 0;
        for (int i=1; i<robotvalue.length; i++) {
            if (robotvalue[i] > maximum) {
                maximum = robotvalue[i];
                index = i;
            }
        }
        MapLocation rlocation = nearbyRobots[index].location;
        if (rc.canAttack(rlocation)) {
            rc.attack(rlocation);
        }
        // PATHFINDING!!!

    }

    static void runBuilder(RobotController rc) throws GameActionException {
        Team ally = rc.getTeam();
        Team opponent = Team.A;
        if (ally == Team.A) {
            opponent = Team.B;
        }
        MapLocation me = rc.getLocation();

            // three possible statuses - need to decide how to set it
            // 0: target repair status, lock onto a normal building and repair it over and over, apply repair to secondary target if primary target is full hp
            // 1: laboratory build/repair status, use recommended coordinates saved and provided (probably recorded) to build specific building
            // 2: same as above but for turrets

        MapLocation currentTargetLocation = me; //goal lab location, goal watchtower prototype location or defending a target
        // INSERT FUNCTION/COMMUNICATION TO DETERMINE THE ABOVE VARIABLES
        int builderStatus = 0; //remove forced initial value once above is in place
        if (builderStatus == 0){
            // first try to repair target, if it doesn't need healing, then search for other viable targets
            RobotInfo[] nearbyRobots = rc.senseNearbyRobots(me, -1, ally);
            int rhealth;
            RobotType rtype;
            int[] robotvalue = new int[nearbyRobots.length];
            int turns = rc.getActionCooldownTurns();
            if ((rc.canRepair(currentTargetLocation)) && (turns == 0)) {
                RobotInfo currentTargetRobot = rc.senseRobotAtLocation(currentTargetLocation);
                rtype = currentTargetRobot.type;
                rhealth = currentTargetRobot.health;
                if ((rtype == RobotType.WATCHTOWER) && (rhealth < 110)) {
                    rc.repair(currentTargetLocation);
                } else if ((rtype == RobotType.ARCHON) && (rhealth < 950)) {
                    rc.repair(currentTargetLocation);
                } else if ((rtype == RobotType.LABORATORY) && (rhealth < 80)) {
                    rc.repair(currentTargetLocation);
                }
            } else if (turns == 0) {
                for (int i = 0; i < nearbyRobots.length; i++) {
                    rtype = nearbyRobots[i].type; // gets type of robot
                    rhealth = nearbyRobots[i].health; // gets health of robot
                    if ((rtype == RobotType.WATCHTOWER)) {
                        robotvalue[i] = 100 - rhealth;
                    } else if ((rtype == RobotType.ARCHON)) {
                        robotvalue[i] = 1300 - rhealth;
                    } else if ((rtype == RobotType.LABORATORY)) {
                        robotvalue[i] = 10 - rhealth;                   }
                }
                int maximum = robotvalue[0];   // find maximum robotvalue and index
                int index = 0;
                for (int i=1; i<robotvalue.length; i++) {
                    if (robotvalue[i] > maximum) {
                        maximum = robotvalue[i];
                        index = i;
                    }
                }
                MapLocation rlocation = nearbyRobots[index].location;
                if (rc.canRepair(rlocation)) {
                    rc.repair(rlocation);
                }
            }
        } else if (((builderStatus == 1) || (builderStatus == 2)) && (rc.canSenseLocation(currentTargetLocation))) {
            // if builder is actively pathfinding to currentTargetLocation, this part of the code can be ignored
            // if builder is within range of currentTargetLocation, check what is there
                // if there is a completed full hp building, switch to mode 0 with the same target location until overridden
                // if there is a partial hp prototype, repair it
                // if there is nothing there, place a prototype
            RobotInfo targetRobot = rc.senseRobotAtLocation(currentTargetLocation);
            Team rteam = targetRobot.team;
            RobotMode rmode = targetRobot.mode;
            RobotType rTargetType = RobotType.ARCHON; // will do nothing if below isn't chosen
            if (builderStatus == 1) {
                rTargetType = RobotType.LABORATORY;
            } else if (builderStatus == 2) {
                rTargetType = RobotType.WATCHTOWER;
            }
            if (rteam == ally) {
                if (rmode == RobotMode.PROTOTYPE) {
                    if (rc.canRepair(currentTargetLocation)) {
                        rc.repair(currentTargetLocation);
                    }
                } else if (rmode == RobotMode.TURRET) {
                    builderStatus = 0;
                    if (rc.canRepair(currentTargetLocation)) {
                        rc.repair(currentTargetLocation);
                    }
                }
            } else if (!(rc.canSenseRobotAtLocation(currentTargetLocation))) {
                Direction targetDirection;
                for (int i = 0; i < directions.length; i++) {
                    if (rc.adjacentLocation(directions[i]) == currentTargetLocation) {
                        if (rc.canBuildRobot(rTargetType, directions[i])) {
                            rc.buildRobot(rTargetType, directions[i]);
                        }
                    }
                }
            } else {
                for (int i = 0; i < directions.length; i++) {
                    if (rc.canBuildRobot(rTargetType, directions[i])) {
                        rc.buildRobot(rTargetType, directions[i]);
                    }
                }
            }
        }
    }
    static void randomMove(RobotController rc) throws GameActionException {
            Direction randdirection = directions[rng.nextInt(directions.length)];
            if (rc.canMove(randdirection)) {
                rc.move(randdirection);
            }
    }
}  
