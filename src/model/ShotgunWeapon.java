package model;

/**
 * Created by Kamil on 2016-05-22.
 */
public class ShotgunWeapon implements Weapon
{
    private final double bulletSpeed = 4;
    private final long delay = 500000000L;
    private final int bulletsPerShot = 7;
    private final int bulletsDispersion = 4;
    private long lastShotTime = 0;
    private Player player;

    public ShotgunWeapon(Player player)
    {
        this.player = player;
    }

    private void shootBullet(long currentTime, double bulletRotation)
    {
        double bulletxPosition = player.getxPosition();
        double bulletyPosition = player.getyPosition();
        double bulletxVelocity = bulletSpeed * Math.sin(Math.toRadians(bulletRotation));
        double bulletyVelocity = -bulletSpeed * Math.cos(Math.toRadians(bulletRotation));
        Bullet bullet = new Bullet(player, bulletxPosition, bulletyPosition, bulletxVelocity, bulletyVelocity, currentTime);
        player.addBullet(bullet);
    }

    @Override
    public void shoot(long currentTime)
    {
        if (currentTime - lastShotTime > delay)
        {
            for (int i = 0; i < bulletsPerShot; ++i)
            {
                double bulletRotation = player.getRotation() + (-bulletsPerShot / 2 + i) * bulletsDispersion;
                shootBullet(currentTime, bulletRotation);
            }
            lastShotTime = currentTime;
        }
    }
}
