## Install
ssh into RG353V
```
cd /userdata/roms/ports
wget https://github.com/grimpirate/RG353V/archive/refs/heads/main.zip
unzip main.zip
cd RG353V-main
chmod 0755 autogen.sh
./autogen.sh
```
Restart device  
PORTS emulator should now have a listing that reads Java\[ports\]

The test application shows the output of pressing any input/button on the device except for: touch, volume, reset, or power.  
SELECT: exit  
START: clear  
FUNCTION: graphics test  
