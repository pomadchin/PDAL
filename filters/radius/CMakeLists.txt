PDAL_ADD_DRIVER(filter radius "RadiusFilter.cpp" "RadiusFilter.hpp" objects)
set(PDAL_TARGET_OBJECTS ${PDAL_TARGET_OBJECTS} ${objects} PARENT_SCOPE)
