package model;

/**
 * Created by Kamil on 2016-05-14.
 */
public class DefaultWeapon implements Weapon
{
    private final double bulletSpeed = 8;
    private final long delay = 100000000L;
    private long lastShotTime = 0;
    private Player player;

    public DefaultWeapon(Player player)
    {
        this.player = player;
    }

    @Override
    public void shoot(long currentTime)
    {
        if (currentTime - lastShotTime > delay)
        {
            double bulletxPosition = player.getxPosition();
            double bulletyPosition = player.getyPosition();
            double bulletxVelocity = bulletSpeed * Math.sin(Math.toRadians(player.getRotation()));
            double bulletyVelocity = -bulletSpeed * Math.cos(Math.toRadians(player.getRotation()));
            Bullet bullet = new Bullet(player, bulletxPosition, bulletyPosition, bulletxVelocity, bulletyVelocity, currentTime);
            player.addBullet(bullet);
            lastShotTime = currentTime;
        }
    }
}
