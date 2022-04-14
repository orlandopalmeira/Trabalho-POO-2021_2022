devices = open("./files/devices_test.txt","w")
people = open("./files/people_test.txt","w")
houses = open("./files/houses_test.txt","w")


# Houses
# Morada(string);[(Room:[d1.d2.d2])];NIF(int);Fornecedor(string)
# 
# 
# People 
# Name(string);NIF(int)
bools = ["true","false"]
# generate devices
# SmartBulb;ID(string);ON/OFF(bool);TONE(int);DIMENSION(double)
# SmartSpeaker;ID(string);ON/OFF(bool);Volume(int);Channel(string);Marca(string)
# SmartCamera;ID(string);ON/OFF(bool);resx(int);resy(int);sizeoffile(float)
for i in range(1,101):
    devices.write("SmartBulb;b{0};{1};1;10\n".format(i,bools[i%2]))
    devices.write("SmartSpeaker;s{0};{1};10;Channel{0};Brand{0}\n".format(i,bools[(i+1)%2]))
    devices.write("SmartCamera;c{0};{1};1080;1920;100\n".format(i,bools[i%2]))

# generate people
# Name;NIF
for i in range(1,21):
    people.write("Person{0};{0}\n".format(i))

providers = ["EDP","Endesa","GoldEnergy","Iberdrola","Galp"]
length = len(providers)
# generate houses
# Morada(string);[(Room:[d1.d2.d2])];NIF(int);Fornecedor(string)
for i in range(1,21):
    houses.write("Street{};[".format(i))
    houses.write("(Sala:[")
    for j in range(5*i-4,5*i-1):
        houses.write("b{0}.s{0}.c{0}.".format(j));
    houses.seek(houses.tell()-1,0);
    houses.write("]),(Quarto:[")
    for j in range(5*i-1,5*i+1):
        houses.write("b{0}.s{0}.c{0}.".format(j))
    houses.seek(houses.tell()-1,0);
    houses.write("])];")
    houses.write("{0};{1}\n".format(i,providers[i%length]))

devices.close()
people.close()
houses.close()
print("Done!")