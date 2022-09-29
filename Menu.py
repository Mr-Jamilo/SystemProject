import csv

OAcode = ""
OAname = ""
distance_from_LPL = ""
distance_from_BOH = ""
print("Welcome to the Flight Planner\n")
def menu():
    choice = input("Please choose one of the following options: \n1)Enter airport details \n2)Enter flight details \n3)Enter price plan and calculate profit \n4)Clear data \n5)Exit \n")
    if choice == "1":
        confirm1 = input("Are you sure you want to enter your airport details? Y/N: ").upper()
        if confirm1 == "N":
            menu()
        elif confirm1 == "Y":
            ADetails()
        else:
            print("Invalid choice, returning back to menu...")
            menu()

    elif choice == "2":
        confirm2 = input("Are you sure you want to enter your flight details? Y/N: ").upper()
        if confirm2 == "N":
            menu()
        elif confirm2 == "Y":
            FDetails()
        else:
            print("Invalid choice, returning back to menu...")
            menu()

    elif choice == "3":
        confirm3 = input("Are you sure you want to enter your price plan to calculate the profit? Y/N: ").upper()
        if confirm3 == "N":
            menu()
        elif confirm3 == "Y":
            PrDetails()
        else:
            print("Invalid choice, returning back to menu...")
            menu()

    elif choice == "4":
        confirm4 = input("Are you sure you want to exit out of the game? Y/N: ").upper()
        if confirm4 == "N":
            Data()
        elif confirm4 == "Y":
            print("Exiting game...")
            exit
        else:
            print("Invalid choice, returning back to menu...")
            menu()

    elif choice == "5":
        confirm4 = input("Are you sure you want to exit out of the flight planner Y/N: ").upper()
        if confirm4 == "N":
            Data()
        elif confirm4 == "Y":
            print("Exiting flight planner...")
            exit
        else:
            print("Invalid choice, returning back to menu...")
            menu()

    else:
        print("Invalid choice, returning back to menu...")
        menu()

def ADetails():
    print("\nLPL - Liverpool John Lennon \nBOH - Bournemouth International")
    UKairport = input("\nPlease enter a UK airport code: ").upper()
    if UKairport == "LPL":
        print("")
    elif UKairport == "BOH":
        print("")
    else:
        print("This is an invalid airport, please try again.")
        ADetails()

    with open("Airports.txt", "r") as txt_file:
        airports = csv.reader(txt_file)
        next(airports)
        for line in airports:
            print(line)
            OAcode = line[0]
            OAname = line[1]
            distance_from_LPL = line[2]
            distance_from_BOH = line[3]
        OAchoice = input("Enter the overseas airport code: ").upper()
        if OAchoice == OAcode:
            print(OAcode)
            print("Overseas airport name:", OAname)
            print("Distance away from Liverpool John Lennon(km)", distance_from_LPL)
            print("Distance away from Bournemouth International(km)", distance_from_BOH)
        
def FDetails():
    FDetails = input("lol")
        
def PrDetails():
    print("lol")

def Data():
    print("lol")
menu()

