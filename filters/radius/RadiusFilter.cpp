/******************************************************************************
 * Copyright (c) 2016, Bradley J Chambers (brad.chambers@gmail.com)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following
 * conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of Hobu, Inc. or Flaxen Geo Consulting nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 ****************************************************************************/

#include "RadiusFilter.hpp"

#include <pdal/KDIndex.hpp>
#include <pdal/util/Utils.hpp>
#include <pdal/pdal_macros.hpp>

#include <chrono>
#include <cctype>
#include <limits>
#include <map>
#include <string>
#include <vector>

namespace pdal
{

static PluginInfo const s_info =
    PluginInfo("filters.radius", "Radius outlier removal",
               "http://pdal.io/stages/filters.radius.html");

CREATE_STATIC_PLUGIN(1, 0, RadiusFilter, Filter, s_info)

std::string RadiusFilter::getName() const
{
    return s_info.name;
}


Options RadiusFilter::getDefaultOptions()
{
    Options options;
    options.add("min_neighbors", 2, "Minimum number of neighbors in radius");
    options.add("radius", 1, "Radius");
    options.add("classify", true, "Apply classification labels?");
    options.add("extract", false, "Extract ground returns?");
    return options;
}


void RadiusFilter::processOptions(const Options& options)
{
    m_min_neighbors = options.getValueOrDefault<int>("min_neighbors", 2);
    m_radius = options.getValueOrDefault<double>("radius", 1);
    m_classify = options.getValueOrDefault<bool>("classify", true);
    m_extract = options.getValueOrDefault<bool>("extract", false);
}


void RadiusFilter::addDimensions(PointLayoutPtr layout)
{
    layout->registerDim(Dimension::Id::Classification);
}


PointViewSet RadiusFilter::run(PointViewPtr inView)
{
    PointViewSet viewSet;
    if (!inView->size())
        return viewSet;
    
    auto t0 = std::chrono::high_resolution_clock::now();
    KD3Index index(*inView);
    index.build();
    auto t1 = std::chrono::high_resolution_clock::now();

    PointViewPtr outView = inView->makeNew();

    auto t2 = std::chrono::high_resolution_clock::now();
    for (PointId i = 0; i < inView->size(); ++i)
    {
        PointRef point = inView->point(i);
        double x = point.getFieldAs<double>(Dimension::Id::X);
        double y = point.getFieldAs<double>(Dimension::Id::Y);
        double z = point.getFieldAs<double>(Dimension::Id::Z);
        
        std::vector<PointId> ids;
        ids = index.radius(x, y, z, m_radius);
        
        if (ids.size() > size_t(m_min_neighbors))
            outView->appendPoint(*inView, i);
    }
    auto t3 = std::chrono::high_resolution_clock::now();
    
    auto d1 = std::chrono::duration_cast<std::chrono::milliseconds>(t1-t0);
    auto d2 = std::chrono::duration_cast<std::chrono::milliseconds>(t3-t2);
    std::cerr << "Build took " << d1.count() << "ms\n";
    std::cerr << "Filtering took " << d2.count() << "ms\n";

    viewSet.insert(outView);
    return viewSet;
}

} // namespace pdal
