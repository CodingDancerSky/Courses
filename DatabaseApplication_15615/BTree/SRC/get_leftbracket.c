#include <stdio.h>
#include <string.h>
#include "def.h"

extern int FreePage(struct PageHdr *PagePtr);
extern struct PageHdr *FetchPage(PAGENO page);
extern void strtolow(char *str);
extern int check_word(char *word);
extern int iscommon(char *word);

int get_leftbracket(char *key, char *result) {
	// check the validation of input.
    if (strlen(key) > MAXWORDSIZE) {
        printf("ERROR:  Length of Prefix Exceeds Maximum Allowed.\n");
        return -1;
    }
    if (check_word(key) == FALSE) {
        return -2;
    }
    if (iscommon(key)) {
        printf("ERROR: \"%s\" is a Common Word.\n", key);
        return -3;
    }

    strtolow(key);

    // implement me!

    return 0;
}

