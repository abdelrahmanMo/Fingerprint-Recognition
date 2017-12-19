# Fingerprint-Recognition
## Abstract
This document explains the processes undertaken throughout the project to complete it, in addition to the results and conclusion
 The project mainly consists of two main phases, preprocessing of Images in the appropriate manner to make it ready for feature extraction.
 Then it is followed by Feature extraction of preprocessed fingerprint and the comparison to an existing fingerprint.
 
## Preprocessing
First step of preprocessing is binarization; which is averaging specific pixels and comparing it to a certain threshold (120)
and then setting it if it’s bigger than one, otherwise put it to zero.
Next comes thinning, which is the elimination of redundant features till ridges become one pixel only, 
thus allowing faster and more efficient feature extraction and axiomatically overall process.

## Minutiae Extraction
This phase is concerned with the extraction of useful features in the preprocessed image, and then the extracted features of the input fingerprint would be reorganized and then compared to the features of existing fingerprints in the database. This is conducted through crossing number algorithm to calculate useful features and then compare them with existing fingerprints.
Results
After implementing the project, Simple Fingerprint Minutiae Extraction using Crossing Number is proved to efficient and fast algorithm, relative to other algorithms widely used, this is part of the reason behind the very common use of this algorithm in many application.
