package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kamil on 2016-05-01.
 */
public class Player
{
    private static final double MAX_VELOCITY = 7;
    private static final double MAX_ACCELERATION = 0.5;
    private static final double SLOWING_SPEED = 0.1;
    private static final double ROTATION_STEP = 4.0;
    private static final int INITIAL_LIVES_NUMBER = 3;
    private static final int NUMBER_OF_WEAPONS = 2;
    private static final long SWITCHING_INTERVAL = 500000000L;

    private Spaceship spaceship;

    private int score;

    private MyShape shape;
    private List<Bullet> bullets;
    private Weapon[] weapons;

    private int currentWeapon;
    private int lives;

    private PlayerState normalState;
    private PlayerState immortalState;
    private PlayerState state;

    private boolean shooting;
    private boolean rotatingRight;
    private boolean rotatingLeft;
    private boolean switching;

    private long lastSwitched = 0;

    public enum State
    {
        NORMAL, IMMORTAL
    }

    public Player(double xPosition, double yPosition)
    {
        spaceship = new Spaceship();
        this.spaceship.setxPosition(xPosition);
        this.spaceship.setyPosition(yPosition);
        this.spaceship.setxVelocity(0);
        this.spaceship.setyVelocity(0);
        this.spaceship.setxAcceleration(0);
        this.spaceship.setyAcceleration(0);
        this.spaceship.setRotation(0);
        initNonSpaceshipFields();
    }

    public Player(double xPosition, double yPosition, double rotation)
    {
        this(xPosition, yPosition);
        this.spaceship.setRotation(rotation);
    }

    public Player(Spaceship spaceship)
    {
        this.spaceship = spaceship;
        initNonSpaceshipFields();
    }

    public State getState()
    {
        return state.getState();
    }

    public void switchWeapon()
    {
        switching = true;
    }

    private void initNonSpaceshipFields()
    {
        weapons = new Weapon[NUMBER_OF_WEAPONS];
        weapons[0] = new DefaultWeapon(this);
        weapons[1] = new ShotgunWeapon(this);
        currentWeapon = 0;
        bullets = new CopyOnWriteArrayList<>(new ArrayList<>());
        shape = new PlayerShapeGenerator().generateShape();
        score = 0;
        lives = INITIAL_LIVES_NUMBER;
        normalState = new NormalPlayerState(this);
        immortalState = new ImmortalPlayerState(this);
        state = normalState;
    }

    public double getxPosition()
    {
        return spaceship.getxPosition();
    }

    public double getyPosition()
    {
        return spaceship.getyPosition();
    }

    public double getxVelocity()
    {
        return spaceship.getxVelocity();
    }

    public double getyVelocity()
    {
        return spaceship.getyVelocity();
    }

    public double getRotation()
    {
        return spaceship.getRotation();
    }

    public MyShape getShape()
    {
        shape.setPosition(spaceship.getxPosition(), spaceship.getyPosition());
        shape.setRotation(spaceship.getRotation());
        return shape;
    }

    public void accelerate()
    {
        spaceship.setxAcceleration(MAX_ACCELERATION * Math.sin(Math.toRadians(spaceship.getRotation())));
        spaceship.setyAcceleration(-MAX_ACCELERATION * Math.cos(Math.toRadians(spaceship.getRotation())));
    }

    public void accelerateReverse()
    {
        spaceship.setyAcceleration(MAX_ACCELERATION * Math.cos(Math.toRadians(spaceship.getRotation())));
        spaceship.setxAcceleration(-MAX_ACCELERATION * Math.sin(Math.toRadians(spaceship.getRotation())));
    }

    private void updateDeceleration()
    {
        double linearVelocityBefore = Math.sqrt(this.spaceship.getxVelocity() * this.spaceship.getxVelocity() + this.spaceship.getyVelocity() * this.spaceship.getyVelocity());
        if (linearVelocityBefore > SLOWING_SPEED)
        {
            if (spaceship.getxAcceleration() == 0)
                this.spaceship.setxVelocity(this.spaceship.getxVelocity() * (linearVelocityBefore - SLOWING_SPEED) / linearVelocityBefore);
            if (spaceship.getyAcceleration() == 0)
                this.spaceship.setyVelocity(this.spaceship.getyVelocity() * (linearVelocityBefore - SLOWING_SPEED) / linearVelocityBefore);
        } else if (spaceship.getxAcceleration() == 0 && spaceship.getyAcceleration() == 0)
        {
            spaceship.setxVelocity(0);
            spaceship.setyVelocity(0);
        }
    }

    private void updateVelocity()
    {
        this.spaceship.setyVelocity(this.spaceship.getyVelocity() + spaceship.getyAcceleration());
        this.spaceship.setxVelocity(this.spaceship.getxVelocity() + spaceship.getxAcceleration());
        double linearVelocity = Math.sqrt(this.spaceship.getxVelocity() * this.spaceship.getxVelocity() + this.spaceship.getyVelocity() * this.spaceship.getyVelocity());
        if (linearVelocity > MAX_VELOCITY)
        {
            this.spaceship.setxVelocity(this.spaceship.getxVelocity() * MAX_VELOCITY / linearVelocity);
            this.spaceship.setyVelocity(this.spaceship.getyVelocity() * MAX_VELOCITY / linearVelocity);
        }
    }

    private void updatePosition()
    {
        this.spaceship.setxPosition(this.spaceship.getxPosition() + spaceship.getxVelocity());
        this.spaceship.setyPosition(this.spaceship.getyPosition() + spaceship.getyVelocity());
    }

    private void resetAcceleration()
    {
        this.spaceship.setxAcceleration(0);
        this.spaceship.setyAcceleration(0);
    }

    private void updateBullets(long currentTime)
    {
        for (Bullet bullet : bullets)
            bullet.update(currentTime);
    }

    private void updateShooting(long currentTime)
    {
        if (shooting)
        {
            weapons[currentWeapon].shoot(currentTime);
            shooting = false;
        }
    }

    private void updateSwitching(long currentTime)
    {
        if (switching)
        {
            if (currentTime - lastSwitched > SWITCHING_INTERVAL)
            {
                currentWeapon = (currentWeapon + 1) % NUMBER_OF_WEAPONS;
                lastSwitched = currentTime;
            }
            switching = false;
        }
    }

    private void updateRotation()
    {
        if (rotatingRight)
        {
            spaceship.setRotation((spaceship.getRotation() + ROTATION_STEP) % 360);
            rotatingRight = false;
        }
        if (rotatingLeft)
        {
            spaceship.setRotation(spaceship.getRotation() - ROTATION_STEP);
            if (spaceship.getRotation() < 0)
                spaceship.setRotation(spaceship.getRotation() + 360);
            rotatingLeft = false;
        }
    }

    private void adjustPositionWhenOffScreen()
    {
        if (this.spaceship.getxPosition() >= GameModel.getGameAreaWidth())
            this.spaceship.setxPosition(this.spaceship.getxPosition() - GameModel.getGameAreaWidth());
        if (this.spaceship.getxPosition() < 0)
            this.spaceship.setxPosition(this.spaceship.getxPosition() + GameModel.getGameAreaWidth());
        if (this.spaceship.getyPosition() >= GameModel.getGameAreaHeight())
            this.spaceship.setyPosition(this.spaceship.getyPosition() - GameModel.getGameAreaHeight());
        if (this.spaceship.getyPosition() < 0)
            this.spaceship.setyPosition(this.spaceship.getyPosition() + GameModel.getGameAreaHeight());
    }

    private void updateSpaceship()
    {
        updateDeceleration();
        updateVelocity();
        updatePosition();
        resetAcceleration();
        updateRotation();
    }

    public void update(long currentTime)
    {
        updateSpaceship();
        updateBullets(currentTime);
        updateShooting(currentTime);
        updateSwitching(currentTime);
        adjustPositionWhenOffScreen();
    }

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }

    public void shoot()
    {
        shooting = true;
    }

    public List<Bullet> getBullets()
    {
        return bullets;
    }

    public void rotateRight()
    {
        rotatingRight = true;
    }

    public void rotateLeft()
    {
        rotatingLeft = true;
    }

    public void removeBullet(Bullet bullet, boolean hit)
    {
        /**
         * TODO: make more elegant score counter; magic numbers
         */
        score = hit ? score + 200 : score - 10; // 200 - points for each asteroid hit, -10 - points for each bullet missed
        bullets.remove(bullet);
    }

    public void die()
    {
        --lives;
    }

    public void playerShotDown()
    {
        state.playerShotDown();
    }

    public void becomeImmortal()
    {
        state = immortalState;
    }

    public void becomeNormal()
    {
        state = normalState;
    }

    public int getScore()
    {
        return score;
    }

    public int getLives()
    {
        return lives;
    }

    public int getCurrentWeapon()
    {
        return currentWeapon;
    }
}
