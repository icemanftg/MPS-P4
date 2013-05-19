Layout Analysis
======

Tehnologii:
	Java 7 SE
	
Coding Style:
	Conform cu:
		http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html

Alte tool-uri si biblioteci:
	Biblioteci puse la dispoziţie de echipa de MPS.
	Maven
	Java SWING

### Enunţ ###

Scopul acestui proiect este de a crea un executabil care să permită corecția erorilor în ceea ce privește gruparea caracterelor în rânduri și a rândurilor în blocuri de text, a paginării, și a conținutului text propriu-zis.

Workflow-ul tipic este următorul:

 + utilizatorul fie încarcă un fișier XML asupra căruia a fost executată analiza de layout, fie selectează un imagine și execută o analiză de layout rulând unul din analizoarele disponibile
 + în acest moment, se încarcă imaginea și la nivelul acesteia pot fi ilustrate componentele imaginii document: litere, rânduri de litere și blocuri text; în funcție de selecția utilizatorului, pot fi vizualizate toate cele 3 niveluri, sau doar anumite categorii
 + dacă asupra textului nu s-a executat analiza OCR, se poate cere analiza aceasta selectând analiza OCR, fie pe toată imaginea, fie doar pe anumite rânduri din imagine
 + rezultatele analizei OCR pot fi corectate selectând linia dorită și modificând textul atașat care trebuie să fie atașat într-o casetă text alăturată
 + dacă niciunul din blocurile text nu este identificat ca fiind număr de pagină, se poate fie rula modulul de numerotare a paginii, fie să se impună ca unul din blocuri să reprezinte numărul de pagină; pentru ca blocul de pagină conține rânduri (de fapt un singur rând la o detecție corectă), numărul poate fi detectat prin OCR sau introdus manual
 + pe lângă aceste acțiuni, trebuie să fie posibilă redimensionarea unirea/spargerea blocurilor text, unirea/spargerea liniilor de litere
 + rezultatul va fi un fișier XML care descrie layout-ul unei imaginii, conform schemei, limitându-se la blocuri cu tipul paragraph și un bloc cu tipul page_number
