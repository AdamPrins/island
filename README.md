# island
This is a hobby project that will be looking into island generation using random points and Delaunay triangulation.
Currently produces tiles with some color purmutations. 

The new branch imageReader focuses on the mosaics part of the generation. The triangle colors are determined by the average colour of all the points contained by the triangle. Canvas size is currently static at 500x500. dynamic resizing will be looked at for the future.

Some examples of the mosaic generation:\
Mosaic of a landscape image, shown beside original\
<img src="https://i.imgur.com/RZ6Wivu.png" width="800">\
\
low resolution image of a bird (1,000 points)\
<img src="https://i.imgur.com/gSjiw10.png" width="500">\
\
Higher resolution (10,000 points)\
<img src="https://i.imgur.com/Z3yqY97.png" width="500">\
\
High resolution (10,000 points), but with center bias for point generation\
<img src="https://i.imgur.com/a5BazXw.png" width="500">\
\
\
\
There are toggles for the points, triangle outlines, and the Circumcircles. \
There are a couple diffrent distribution options. \
Ocean and coastal points can be generated 

Preview with only triangles using circle distribution\
<img src="https://i.imgur.com/0PoMXlV.png" width="500">\
\
Preview with outline and points turned on\
<img src="https://i.imgur.com/GLC7Su5.png" width="500">\
\
Preview with only triangles using center distribution\
<img src="https://i.imgur.com/98uC0zJ.png" width="500">\
\
Preview with points and circles turned on\
<img src="https://i.imgur.com/PdjXp1a.png" width="500">\
\


-Adam Prins
