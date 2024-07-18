#!/bin/sh

DIR="$(dirname "$(readlink -f "${0}")")"
wget https://corretto.aws/downloads/latest/amazon-corretto-22-aarch64-linux-jdk.tar.gz
gzip -dk amazon-corretto-22-aarch64-linux-jdk.tar.gz
tar -xvf amazon-corretto-22-aarch64-linux-jdk.tar

JAVA_HOME="$(ls | grep linux-aarch64)"

SCRIPT=build.sh
echo "#!/bin/sh" > $SCRIPT
echo "" >> $SCRIPT
echo "DIR=\"\$(dirname \"\$(readlink -f \"\${0}\")\")\"" >> $SCRIPT
echo "export JAVA_HOME=\$DIR/$JAVA_HOME" >> $SCRIPT
echo "\$JAVA_HOME/bin/javac \\" >> $SCRIPT
echo "  -cp \$DIR \\" >> $SCRIPT
echo "  com/grimpirate/App.java" >> $SCRIPT

SCRIPT=Java.sh
echo "#!/bin/sh" > $SCRIPT
echo "" >> $SCRIPT
echo "DIR=\"\$(dirname \"\$(readlink -f \"\${0}\")\")\"" >> $SCRIPT
echo "export JAVA_HOME=\$DIR/jre" >> $SCRIPT
echo "export JSD_PIXMAPS=shared" >> $SCRIPT
echo "\$JAVA_HOME/bin/java \\" >> $SCRIPT
echo "  -Djava.awt.headless=true \\" >> $SCRIPT
echo "  -Dsun.java2d.opengl=true \\" >> $SCRIPT
echo "  -Dsun.java2d.pmoffscreen=true \\" >> $SCRIPT
echo "  -cp \$DIR \\" >> $SCRIPT
echo "  com.grimpirate.App" >> $SCRIPT

chmod 0755 *.sh
$DIR/build.sh

$JAVA_HOME/bin/jlink --no-header-files --no-man-pages --compress=zip-9 --add-modules java.desktop --output "jre"

rm com/grimpirate/*.java
rm jre/release
rm -rf jre/legal
rm jre/bin/keytool
rm -rf $JAVA_HOME
rm amazon-corretto-22-aarch64-linux-jdk.tar*
rm build.sh
rm autogen.sh
rm README.md
rm LICENSE
