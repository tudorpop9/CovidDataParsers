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
void removeBrackets(char *str) 
{ 
    int count = 0;  
    for (int i = 0; str[i]; i++) 
        if (str[i] != '[' || str[i] != ']' || str[i] != '{' || str[i] != '}') 
            str[count++] = str[i]; 
    str[count] = '\0'; 
}

void processGeo(char* longitude, char* latitude, const char* buff){
    
    char copyBuff[100];
    strcpy(copyBuff,buff);
    
    strcpy(copyBuff, strstr(copyBuff, "{") + 1);  
    removeSpaces(copyBuff);
    removeOuotation(copyBuff);
    //removeBrackets(copyBuff);
    
    strcpy(longitude, strtok(copyBuff, ","));
    strcpy(latitude, strtok(NULL, ","));
    latitude[strlen(latitude)-3]='\0';
}


int main(int argc, char* args[]){
    FILE* fd = fopen("geomData.txt", "r");
    FILE* fLon = fopen("FormatedLongitude.txt", "w");
    FILE* fLat = fopen("FormatedLatitude.txt", "w");

    if(fd == NULL || fLon == NULL || fLat == NULL){
        printf("Could not open the file \n");
        exit(1);
    }

    char buff[100], longitude[100], latitude[100];
   
    //Column name
    fgets(buff, 100, fd);
    fprintf(fLon, "%s\n", "Longitude");
    fprintf(fLat, "%s\n", "Latitude");

    while (fgets(buff, 100, fd))
    {
        if(strstr(buff,"GeoPosition") != NULL){
            processGeo(longitude, latitude, buff);
        }else{
            strcpy(longitude, "missing");
            strcpy(latitude, "missing");  
        }
        fprintf(fLon, "%s\n", longitude);
        fprintf(fLat, "%s\n", latitude);

    }
    
    fclose(fd);
    fclose(fLon);
    fclose(fLat);
    return 0;
}