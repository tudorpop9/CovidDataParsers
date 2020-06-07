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

void getAgeInterval(char* interval, const char* buff){
    int nr = atoi(buff);
    int cifraZecilor = nr/10;

    switch (cifraZecilor)
    {
    case 0:
        strcpy(interval, "0-9");
        break;
    case 1:
        strcpy(interval, "10-19");
        break;
    case 2:
        strcpy(interval, "20-29");
        break;
    case 3:
        strcpy(interval, "30-39");
        break;
    case 4:
        strcpy(interval, "40-49");
        break;
    case 5:
        strcpy(interval, "50-59");
        break;
    case 6:
        strcpy(interval, "60-69");
        break;
    case 7:
        strcpy(interval, "70-79");
        break;
    case 8:
        strcpy(interval, "80-89");
        break;
    case 9:
    case 10:
    case 11:
    default:
        strcpy(interval, "90-inf");
        break;
    }
}

void processInterval(char* interval, const char* buff){
    char lo[50], hi[50];
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    removeSpaces(copyBuff);
    char* sample = strtok(copyBuff, ",");
    sscanf(sample, "Interval[{%s", lo);
    sscanf(strtok(NULL, ","), "%s}]", hi);
    
    if(strstr(hi,"Infinity")){
        getAgeInterval(interval, "95");
        
    }else{
        int loLim = atoi(lo);
        int hiLim = atoi(hi);
        if(hiLim - loLim <= 20){
            getAgeInterval(interval,lo);
        }else{
            strcpy(interval, "missing");
        }
    }

}


int main(int argc, char* args[]){
    FILE* fd = fopen("ages.txt", "r");
    FILE* fRez = fopen("FormatedAge.txt", "w");

    if(fd == NULL || fRez == NULL){
        printf("Could not open the file \n");
        exit(1);
    }

    char buff[100],interval[100];
   
    //Column name
    fgets(buff, 100, fd);
    fprintf(fRez, "%s\n", buff);

    while (fgets(buff, 100, fd))
    {
        if(strstr(buff,"Interval") != NULL){
            processInterval(interval, buff);
        }else if (strstr(buff,"Missing") != NULL){
            strcpy(interval, "missing");
           
        }else{
            getAgeInterval(interval, buff);
        }
        fprintf(fRez, "%s\n", interval);

    }
    



    fclose(fd);
    fclose(fRez);
}