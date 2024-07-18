#!/bin/sh

DIR="$(dirname "$(readlink -f "${0}")")"
wget https://corretto.aws/downloads/latest/amazon-corretto-22-aarch64-linux-jdk.tar.gz
gzip -dk amazon-corretto-22-aarch64-linux-jdk.tar.gz
tar -xvf amazon-corretto-22-aarch64-linux-jdk.tar
rm amazon-corretto-22-aarch64-linux-jdk.tar*

JAVA_HOME="$(ls | grep amazon)"

SCRIPT=build.sh
echo "#!/bin/sh" > $SCRIPT
echo "" >> $SCRIPT
echo "DIR=\"\$(dirname \"\$(readlink -f \"\${0}\")\")\"" >> $SCRIPT
echo "export JAVA_HOME=\$DIR/$JAVA_HOME" >> $SCRIPT
echo "\$JAVA_HOME/bin/javac \\" >> $SCRIPT
echo "  -cp \$DIR \\" >> $SCRIPT
echo "  com/grimpirate/App.java" >> $SCRIPT
echo "rm \$DIR/com/grimpirate/*.java" >> $SCRIPT

SCRIPT=Java.sh
echo "#!/bin/sh" > $SCRIPT
echo "" >> $SCRIPT
echo "DIR=\"\$(dirname \"\$(readlink -f \"\${0}\")\")\"" >> $SCRIPT
echo "export JAVA_HOME=\$DIR/$JAVA_HOME" >> $SCRIPT
echo "export JSD_PIXMAPS=shared" >> $SCRIPT
echo "\$JAVA_HOME/bin/java \\" >> $SCRIPT
echo "  -Djava.awt.headless=true \\" >> $SCRIPT
echo "  -Dsun.java2d.opengl=true \\" >> $SCRIPT
echo "  -Dsun.java2d.pmoffscreen=true \\" >> $SCRIPT
echo "  -cp \$DIR \\" >> $SCRIPT
echo "  com.grimpirate.App" >> $SCRIPT

chmod 0755 *.sh
$DIR/build.sh
rm build.sh
rm autogen.sh
