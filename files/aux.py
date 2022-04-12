devices = open("./files/devices.txt","w")
people = open("./files/people.txt","w")
houses = open("./files/houses.txt","w")

# generate devices
# SmartBulb;ID;Tone;Dimension
# SmartSpeaker;ID;Channel;Brand
# SmartCamera;ID;ResX;ResY;FileSize
for i in range(1,101):
    devices.write("SmartBulb;b{};1;10\n".format(i))
    devices.write("SmartSpeaker;s{0};Channel{0};Brand{0}\n".format(i))
    devices.write("SmartCamera;c{};1080;1920;100\n".format(i))

# generate people
# Name;NIF
for i in range(1,21):
    people.write("Person{0};{0}\n".format(i))

# generate houses
# Morada;Owner;EnergyProvider;Devices;DevicesPerRoom
for i in range(1,21):
    houses.write("Street{0};Person{0};EDP;[".format(i))
    houses.write("(Sala:[")
    for j in range(5*i-4,5*i-1):
        houses.write("b{0}.s{0}.c{0}.".format(j));
    houses.seek(houses.tell()-1,0);
    houses.write("]),(Quarto:[")
    for j in range(5*i-1,5*i+1):
        houses.write("b{0}.s{0}.c{0}.".format(j))
    houses.seek(houses.tell()-1,0);
    houses.write("])]\n")

devices.close()
people.close()
houses.close()
print("Done!")