#!/bin/sh
echo Création de la préparation pour la version 4.10.1
echo Paramètres:
echo "- sb.version : " 4.10.1
echo "- basedir : " /home/favdb/ext/SB4.10.1/code
echo "- sb.debian.distdir : " /home/favdb/ext/SB4.10.1/code/distrib/debian-package
echo "- sb.debian.app : " /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/ostorybook
echo "- sb.debian.deployment.dir : " /home/favdb/ext/SB4.10.1/code/deployment/linux/debian
mkdir /home/favdb/ext/SB4.10.1/code/distrib/debian-package
echo Copie debian-package
cp -R /home/favdb/ext/SB4.10.1/code/deployment/linux/debian/DEBIAN /home/favdb/ext/SB4.10.1/code/distrib/debian-package
cp -R /home/favdb/ext/SB4.10.1/code/deployment/linux/debian/usr /home/favdb/ext/SB4.10.1/code/distrib/debian-package
sed -i -e "s/@version@/4.10.1/g" /home/favdb/ext/SB4.10.1/code/distrib/debian-package/DEBIAN/control
sed -i -e "s/@version@/4.10.1/g" /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/applications/ostorybook.desktop
sed -i -e "s/@version@/4.10.1/g" /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/doc/ostorybook/changelog
gzip -9 /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/doc/ostorybook/changelog
echo Copie application
mkdir /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/ostorybook
cp -R /home/favdb/ext/SB4.10.1/code/dist/* /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/ostorybook
rm /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr/share/ostorybook/LICENSE.txt
echo Modification des droits
find /home/favdb/ext/SB4.10.1/code/distrib/debian-package/usr -type d -exec chmod 755 {} +
chmod -R 755 /home/favdb/ext/SB4.10.1/code/distrib/debian-package/DEBIAN
echo Construction du paquet DEBIAN
dpkg-deb --build /home/favdb/ext/SB4.10.1/code/distrib/debian-package /home/favdb/ext/SB4.10.1/code/distrib/oStorybook-4.10.1.deb
echo Nettoyage...
rm -r -f /home/favdb/ext/SB4.10.1/code/distrib/debian-package
echo Construction du paquet RPM
cd /home/favdb/ext/SB4.10.1/code/distrib
alien -r /home/favdb/ext/SB4.10.1/code/distrib/oStorybook-4.10.1.deb