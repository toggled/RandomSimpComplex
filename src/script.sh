#!/bin/bash
../LibLandscape/PlotsOfLandscapesViaScripts /Users/naheed/IdeaProjects/TD1/RandomSimpComplex/output/barcodes/generatedbars -1 -1
filenames=`ls ../output/barcodes/*.bar_GnuplotScript`
for eachfile in $filenames
do
   echo $eachfile
done

extension='.svg'


for eachfile in $filenames
do
    plotfilename=$eachfile$extension
    gnuplot <<- EOF
        set term svg
        set output "${plotfilename}"
        load "${eachfile}"
EOF
done
