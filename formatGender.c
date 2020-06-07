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

void processGender(char* gender, const char* buff){
    char str[50];
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    removeSpaces(copyBuff);
    removeOuotation(copyBuff);
    sscanf(copyBuff, "Entity[Gender,%s]", str);
    str[strlen(str) - 1] = '\0';
    
    strcpy(gender, str);

}


int main(int argc, char* args[]){
    FILE* fd = fopen("genders.txt", "r");
    FILE* fRez = fopen("FormatedGender.txt", "w");

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
        if(strstr(buff,"Entity") != NULL){
            processGender(interval, buff);
        }else{
            strcpy(interval, "missing");  
        }
        fprintf(fRez, "%s\n", interval);

    }
    
    fclose(fd);
    fclose(fRez);
}