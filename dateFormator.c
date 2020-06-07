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
void replaceChar(char *str, char target, char replacer) 
{ 
    int n = strlen(str);
    for(int i=0;i<n;i++){
        if(str[i] == target){
            str[i] = replacer;
        }
    }
}

void processDate(char* date, const char* buff){
    char str[50];
    char copyBuff[100];
    char buffToken[100];
    strcpy(copyBuff,buff);
    strtok(copyBuff,"{"); // uninterested in this section of the string

    strcpy(buffToken, strtok(NULL,"{"));
    strcpy(str, strtok(buffToken,"}"));
    //Str should hold the date data now
    
    removeSpaces(str);
    replaceChar(str, ',', '/');

    strcpy(date, str);

}


int main(int argc, char* args[]){
    FILE* fd = fopen("dateData.txt", "r");
    FILE* fRez = fopen("FormatedDate.txt", "w");

    if(fd == NULL || fRez == NULL){
        printf("Could not open the file \n");
        exit(1);
    }

    char buff[100], date[100];
   
    //Column name
    fgets(buff, 100, fd);
    fprintf(fRez, "%s", buff);

    while (fgets(buff, 100, fd))
    {
        if(strstr(buff,"DateObject") != NULL){
            processDate(date, buff);
        }else{
            strcpy(date, "missing");  
        }
        fprintf(fRez, "%s\n", date);

    }
    
    fclose(fd);
    fclose(fRez);
}