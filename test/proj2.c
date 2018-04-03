/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Joe
 *
 * Created on March 29, 2018, 10:24 AM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>
#include "proj2.h"


void printCurrentState(struct process *processes,int time){
    if(processes->burstime>0){
        printf("\n system Time %i Process number %i is running",time,processes->pid);
    }else{
        printf("\n system Time %i Process number %i is finished.........",time,processes->pid);
        processes->finishtime = time;
    }
    
}
void CalcAvgTurnArround(struct process *processes,int *numprocesses){
    int itr =0;
    int totaltime=0;
    while(itr < *numprocesses){
        totaltime += (processes+itr)->finishtime-(processes+itr)->arrivaltime;
        itr++;
    }
    printf("\navg Turnarround time: %2.1f",((float)(totaltime))/ ((float)*numprocesses));
}
void CalcAvgCpu(struct process *processes, int *numprocesses, int totalsystime){
    int itr =0;
    int totaltime=0;
    while(itr < *numprocesses){
        totaltime += (processes+itr)->finishtime-(processes+itr)->starttime;
        itr++;
    }
    printf("\ncpu usage: %2.1f percent",(((float)totaltime)/((float)totalsystime-1))*100);
}
void CalcAvgWait(struct process *processes, int *numprocesses){
    int itr =0;
    int waitaccum= 0;
    while (itr<*numprocesses){
        waitaccum += (processes+itr)->totalwait;
        itr++;
    }
    printf("\nAvg wait time: %2.1f",((float)waitaccum)/((float)*numprocesses));
}
void CalcAvgResponse(struct process *processes, int *numprocesses){
    int itr =0;
    int responseaccum= 0;
    while (itr<*numprocesses){
        responseaccum += (processes+itr)->finishtime-(processes+itr)->starttime;
        itr++;
    }
    printf("\nAvg Response time: %2.1f",((float)responseaccum)/((float)*numprocesses));
}
void printStatResults(struct process *processes, int *numprocesses,int totaltime){
    CalcAvgTurnArround(processes,numprocesses);
    CalcAvgCpu(processes,numprocesses,totaltime);
    CalcAvgWait(processes,numprocesses);
    CalcAvgResponse(processes,numprocesses);
}
void SRTF(struct process *processes,int *numprocesses){
    

}
void RR(struct process *processes,int *numprocesses,int timeswap){
    struct current = findsmallest();
}
void FCFS(struct process *processes,int *numprocesses){
    int time =0;
    int processitr =0;
    int itr=1; 
    struct process *ogprocesses = processes;
    while(itr<=*numprocesses){
        if((processes+processitr)->burstime <= 0 && (processes+processitr)->pid !=0){
            printCurrentState((processes+processitr),time);
            processitr++;
            (processes+processitr)->starttime = time;
            itr++;
        }else if((processes+processitr)->burstime >= 0 && (processes+processitr)->arrivaltime>=time){
            (processes+processitr)->starttime = time;
        }
        if((processes+processitr)->arrivaltime<=time){
            printCurrentState((processes+processitr),time);
            (processes+processitr)->burstime--;
        }
        int itr2 = 0;
        while(itr2<*numprocesses){
            if((processes+itr2)!= (processes+processitr) && (processes + itr2)->burstime!=0 && (processes + itr2)->arrivaltime<time){
                (processes+itr2)->totalwait++;
            }
            itr2++;
        }
        Sleep(1);
        time ++;
        
    }
    printf("Finished all processes");
    printStatResults(ogprocesses,numprocesses,time);
   
}
struct process* nextprocess(struct process *processes){
    return processes++;
}
int main(int argc, char** argv) {
    // initialize the cpu buffer
    int *numprocesses = (int*)malloc(sizeof(int));
    struct process *processes = (struct process*)malloc(sizeof(struct process)*20);
   
    // parse input file
    int itr = 0;
    char *lineBuf = (char*)malloc(sizeof(char)*30);
    FILE *processfile;
    processfile = fopen(argv[1],"r");
    if(processfile == NULL){
        printf("\n no file");
    }
    *numprocesses = 0;
    char *strtemp = (char*)malloc(sizeof(char)*15);
    
    while(fgets(lineBuf,30,processfile) != NULL){
        strtemp = strtok(lineBuf," ");
        (processes+ itr)->pid = atoi(strtemp);
        strtemp = strtok(NULL, " ");
        (processes + itr)->arrivaltime = atoi(strtemp);
        strtemp = strtok(NULL, " ");
        (processes + itr)->burstime = atoi(strtemp);
        (processes + itr)->finishtime = 0;
        (processes + itr)->starttime = 0;
        (*numprocesses)++;
        itr++;
    }
    if(strcmp(argv[2],"FCFS")==0){
        printf("\nFCFS number of processes: %i\n",*numprocesses);
        FCFS(processes,numprocesses);
    }else if(strcmp(argv[2],"RR")==0){
        RR(processes,numprocesses,atoi(argv[3]));
    }else if(strcmp(argv[2],"SRTF")==0){
        SRTF(processes,numprocesses);
    }

    return (EXIT_SUCCESS);
}


