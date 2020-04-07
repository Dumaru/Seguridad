import binascii

binary = "1	0	1	0	0	0	0	0	1	0	1	1	0	1	0	0	1	0	1	0	1	0	1	1	0	0	1	0	0	0	1	1	0	0	1	1	1	1	1	0	1	1	0	1	0	0	0	1	1	1	1	1	0	1	1	0	0	0	0	0	0	1	1	1"
binary = "1	0	1	1	0	1	0	0	1	1	0	1	1	1	1	1	0	0	0	0	0	1	1	1	0	0	0	1	1	0	0	1	1	1	1	1	0	0	1	1	0	0	1	1	0	1	1	1"


binary = "".join([s for s in binary if s == "1" or s == "0"])
# print(binary)
hexa = "2d0d3c07061f0c37"

intNumberFromHex = int(hexa, 16)
intNumberFromBin = int(binary, 2)
scale = 16 ## equals to hexadecimal

num_of_bits = 8

msg = (f"\nBinary {binary} = Int {intNumberFromBin}"
       f"\nHex {hexa} = Int {intNumberFromHex}"
       f"\nBinary {binary} = Hexa {hex(intNumberFromBin)}"
       f"\nHex {hexa} = Binary {bin(int(hexa, scale))[2:].zfill(32)}"
       )
print(msg)

def cambios(stringHexas: str):
        hexas = list()
        hexas = [(line.split(',')[0].strip(),line.split(',')[1].strip()) for line in stringHexas.split('\n') if len(line)>4]
        # print(hexas)
        for cambio in hexas:
                print(cambio[0], "   ->   " ,cambio[1], " XOR ", end="")
                xorResultDec = int(cambio[0],16) ^ int(cambio[1],16)
                xorResultHex = hex(xorResultDec)
                xorResultBin = bin(xorResultDec)[2:].zfill(64)
                i = 0
                cantidaCambiados = 0
                for s in xorResultBin:
                        if s == "1":
                                cantidaCambiados += 1
                print(xorResultBin," Cambios = ", cantidaCambiados)



hexasC = """
675f14dc40C07C02,675f14dc42C07C02\n
40C07C02BFDA44F5,42C07C02BFDA04E5\n
BFDA44F597958A2C,BFDA04E59E9182AD\n
97958A2C199E1A2F,9E9182AD70611D77\n
199E1A2F12754629,70611D774BF1F1E\n
1275462941565F5A,4BF1F1EBB26F6EE\n
41565F5ABE43A19A,BB26F6EE43458DA8\n
BE43A19A9D1555B5,43458DA8FA2C3F22\n
9D1555B529902960,FA2C3F22F59D2162\n
29902960D074386	,F59D2162A75E401B\n
D0743862D79A5FD	,A75E401B7E78A96F\n
2D79A5FD72E5A019,7E78A96FBB9F96DB\n
72E5A0194FADFD27,BB9F96DBDF2AF0AD\n
4FADFD273A4B2229,DF2AF0ADB42CCA32\n
3A4B2229E26C2482,B42CCA325946CB45\n
43F7CA2BE26C2482,DF8CF6325946CB45\n
"""

hexasC = """
675f14dc40C07C02,675f14dc42D07802\n
40C07C02BFDA44F5,42D078021BBF056D\n
BFDA44F597958A2C,1BBF056DFBED5422\n
97958A2C199E1A2F,FBED542252A72F27\n
199E1A2F12754629,52A72F273323883\n
1275462941565F5A,33238838FF601D\n
41565F5ABE43A19A,8FF601D7F2E2460\n
BE43A19A9D1555B5,7F2E246043C78247\n
9D1555B529902960,43C7824764C76A47\n
29902960D074386	,64C76A47CE46D072\n
D0743862D79A5FD	,CE46D072634F7B66\n
2D79A5FD72E5A019,634F7B6661F9197E\n
72E5A0194FADFD27,61F9197E7DB397DE\n
4FADFD273A4B2229,7DB397DE21442622\n
3A4B2229E26C2482,21442622FD889944\n
43F7CA2BE26C2482,4FE16254FD889944\n
"""

cambios(hexasC)