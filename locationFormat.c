#include<stdio.h>
#include<stdlib.h>
#include<string.h>


// Function to remove all spaces from a given string 
void removeSpaces(char *str) 
{ 
    // To keep track of non-space character count 
    int count = 0; 
  
    // Traverse the given string. If current character 
    // is not space, then place it at index 'count++' 
    for (int i = 0; str[i]; i++) 
        if (str[i] != ' ') 
            str[count++] = str[i]; // here count is 
                                   // incremented 
    str[count] = '\0'; 
} 
void removeOuotation(char *str) 
{ 
    int count = 0;  
    for (int i = 0; str[i]; i++) 
        if (str[i] != '"') 
            str[count++] = str[i]; 
    str[count] = '\0'; 
}

void processCityBuff(char* city, char* region, char* country, const char* buff){
    char str[50];
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    removeSpaces(copyBuff);
    removeOuotation(copyBuff);
    strcpy(copyBuff, strstr(copyBuff,"{")+1);  ////Interest part

    strcpy(city, strtok(copyBuff,","));
    strcpy(region, strtok(NULL,","));

    strcpy(str, strtok(NULL,","));
    str[strlen(str)-3] = '\0'; //  China}] ---> China\0] ---> China when printed
    strcpy(country, str);
}

void processRegionBuff(char* region, char* country, const char* buff){
    char str[50];
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    removeSpaces(copyBuff);
    removeOuotation(copyBuff);
    strcpy(copyBuff, strstr(copyBuff,"{")+1);  ////Interest part

    strcpy(region, strtok(copyBuff,","));

    strcpy(str, strtok(NULL,","));
    str[strlen(str)-3] = '\0'; //  China}] ---> China\0] ---> China when printed
    strcpy(country, str);
}

void processCountryBuff(char* country, const char* buff){
    char str[50];
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    removeSpaces(copyBuff);
    removeOuotation(copyBuff);
    strcpy(copyBuff, strstr(copyBuff,"["));  ////Interest part

    //Dummy read to jump over "Country" string
    strtok(copyBuff,",");

    strcpy(str, strtok(NULL,","));
    str[strlen(str)-2] = '\0'; //  China] ---> China\0 ---> China when printed
    strcpy(country, str);
}


int main(int argc, char* args[]){
    FILE* fCity = fopen("cities.txt", "r");
    FILE* fRegion = fopen("regions.txt", "r");
    FILE* fCountry = fopen("coutries.txt", "r");
    FILE* fRezCity = fopen("FormatedCity.txt", "w");
    FILE* fRezRegion = fopen("FormatedRegion.txt", "w");
    FILE* fRezCountry = fopen("FormatedCountry.txt", "w");

    if(fCity == NULL || fRegion == NULL || fCountry == NULL || fRezCity == NULL || fRezRegion == NULL || fRezCountry == NULL){
        printf("Could not open a file \n");
        exit(1);
    }

    char buffCity[100], buffRegion[100], buffCountry[100];
    //char outputCity[100], outputRegion[100], outputCountry[100];
    char cityCity[100], cityRegion[100], cityCountry[100];
    char countryCountry[100], regionRegion[100], regionCountry[100];
   
    //Column name
    fgets(buffCity, 100, fCity);
    fgets(buffRegion, 100, fRegion);
    fgets(buffCountry, 100, fCountry);
    fprintf(fRezCity, "%s\n", buffCity);
    fprintf(fRezRegion, "%s\n", buffRegion);
    fprintf(fRezCountry, "%s\n", buffCountry);

    while (fgets(buffCity, 100, fCity) && fgets(buffRegion, 100, fRegion) && fgets(buffCountry, 100, fCountry))
    {
        // City
        if(strstr(buffCity,"City") != NULL){
            processCityBuff(cityCity, cityRegion, cityCountry, buffCity);
        }else{
            strcpy(cityCity, "missing");  
        }

        //Region
        if(strstr(buffRegion,"AdministrativeDivision") != NULL){
            processRegionBuff(regionRegion, regionCountry, buffRegion);
        }else{
            strcpy(regionRegion, "missing");  
        }

        //Country
        if(strstr(buffCountry,"Country") != NULL){
            processCountryBuff(countryCountry, buffCountry);
        }else{
            strcpy(countryCountry, "missing");  
        }


        //Daca am date despre regiune, dar nu am despre country, ce gasesc la regiune pun a Country
        if(strcmp(regionRegion, "missing") != 0 && strcmp(countryCountry, "missing") == 0){
            strcpy(countryCountry, regionCountry);
        }

        //Daca am gasit date despre oras
        if(strcmp(cityCity, "missing") != 0){
            
            //Dar nu am gasit date despre regiune, ce am gasit in oras, pun la region
            if(strcmp(regionRegion, "missing") == 0){
                strcpy(regionRegion, cityRegion);
            }

            //Idem pentru country
            if(strcmp(countryCountry, "missing") == 0){
                strcpy(countryCountry, cityCountry);
            }
        }


        fprintf(fRezCity, "%s\n", cityCity);
        fprintf(fRezRegion, "%s\n", regionRegion);
        fprintf(fRezCountry, "%s\n", countryCountry);

    }
    
    fclose(fRezCity);
    fclose(fRezRegion);
    fclose(fRezCountry);
    fclose(fCity);
    fclose(fRegion);
    fclose(fCountry);
}
