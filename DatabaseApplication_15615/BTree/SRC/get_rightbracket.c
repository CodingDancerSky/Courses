#include "def.h"

extern int FreePage(struct PageHdr *PagePtr);
extern struct PageHdr *FetchPage(PAGENO page);
extern PAGENO treesearch_page(PAGENO PageNo, char *key);
extern void strtolow(char *str);
extern int check_word(char *word);
extern int iscommon(char *word);
extern int CompareKeys(char *Key, char *Word);

int containKey(char* key, char* key_stored) {
	int i;

	for(i = 0; i < strlen(key); ++i) {
		if(strlen(key_stored) <= i || key[i] != key_stored[i])
			return 0;
	}

	return 1;
}


int get_rightbracket(char *key, char *result) {

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
    struct PageHdr *PagePtr;
	struct KeyRecord *index;
	int find = 2;
    // implement me!
    const PAGENO leaf_page = treesearch_page(ROOT, key);
    PAGENO page = leaf_page;

    printf("found the next key without \"%s\" as its prefix:\n", key);

    // reverse the leaf pages
    while(find > 0 && page != NULLPAGENO) {
        PagePtr = FetchPage(page);
        index = PagePtr->KeyListPtr;
        int count = PagePtr->NumKeys;

        while(count > 0) {
        	char *key_stored = index->StoredKey;

        	if((CompareKeys(key, key_stored) == 1 && !(containKey(key, key_stored)))) {
        		result = (char *) realloc(result, (strlen(key_stored) + 1) * sizeof(char));
        		strcpy(result, key_stored);
        		printf("%s\n", result);
        		return 0;
        	}
        	index = index->Next;
        	count = count - 1;
        }

    PAGENO p_next = PagePtr->PgNumOfNxtLfPg;
    FreePage(PagePtr);
    page = p_next;    

    }

    // couldn't find the key
    // result = (char *) realloc(result, sizeof(char) * 10);
    // strcpy(result, "*NONE*");
    printf("*NONE*\n");
    
    FreePage(PagePtr);
    free(index);

    return 0;
}
