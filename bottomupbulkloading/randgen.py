from random import randint

target = open("input.txt", 'w')

for i in range(0,100):
	#int j;
	for j in range(0,5):
		target.write(chr(randint(97,122)))
	
	target.write("\n")

target.close()
