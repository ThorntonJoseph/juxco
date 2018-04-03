/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   proj2.h
 * Author: Joe
 *
 * Created on April 2, 2018, 9:27 AM
 */

#ifndef PROJ2_H
#define PROJ2_H

#ifdef __cplusplus
extern "C" {
#endif
    
    struct process{
        int totalwait;
        int starttime;
        int finishtime;
        int arrivaltime;
        int burstime;
        int pid;
    };

#ifdef __cplusplus
}
#endif

#endif /* PROJ2_H */

