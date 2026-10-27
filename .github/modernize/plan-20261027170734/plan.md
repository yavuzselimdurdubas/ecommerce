# Modernization Plan: Java Cloud Readiness

**Project**: demo

## Technical Framework

- **Language**: Java 17
- **Framework**: Spring Boot 4.1.1, Spring Data JPA
- **Build Tool**: Maven
- **Database**: H2 (runtime dependency); Azure target not specified
- **Key Dependencies**: Spring Boot Data JPA, Spring Web MVC, H2

## Overview

This plan addresses the three categories selected from assessment report
`report-20261027170734`. It raises the Java runtime to the latest LTS target,
addresses the detected Jakarta Persistence use for Azure readiness, and adds
container support. The assessment also contains a restricted-configuration
finding, which is not included because its category was not selected.

The modernization goals are to:

- Bring the application runtime up to date while retaining its current
  application framework and behavior.
- Prepare the existing persistence layer for use with Azure.
- Make the application container-ready.

The exact Azure database service was not specified and is not selected by this
plan. No Azure deployment or infrastructure provisioning is included.

## Migration Impact Summary

| Application | Original Service | New Azure Service | Authentication | Comments |
|-------------|------------------|-------------------|----------------|----------|
| demo | Java 17 runtime | Java 25 runtime | N/A | Latest LTS target |
| demo | Jakarta Persistence / H2 | Azure database (not specified) | Not specified | Preserve persistence behavior |
| demo | No container image | Container-ready application | N/A | Dockerfile to be created |

