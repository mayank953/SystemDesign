package Uber.VersionProblem;


import java.util.ArrayList;
import java.util.List;
public class VersionProblem {
}




// Background of the problem
// The storage team is developing a version management system. Every night we built one or a few new releases. The versions could be compatible, which means the system could be upgraded without the data migration; otherwise, we have to upgrade the data before deploying the new release binaries (typically with a MapReduce job to rebuild the data). The compatibility is transitive, which means if version a is compatible with version b, version b is compatible with c, then a is compatible with c. If any of them are incompatible, it makes a to c incompatible. We already have a compatible check test (Jenkins job). After each release, we only run the compatible test against the previous version.
// Question
// Assume the version number is an integer, design and implement 2 APIs for the version compatibility management system, here is the APIs:
//    public void addNewVersion(int ver, boolean isCompatibleWithPrev)
//     {
//     }

//     public boolean isCompatible(int srcVer, int targetVer)
//     {
//     }

// addNewVersion() is called by the build system, when a new version is built and needs to add to the system, we already know if it's compatible with the previous one or not. isCompatible() is used by upgrading the system and deciding if a migration is needed.

class Version {
    private int identifier;
    private boolean isCompatible;

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    public void setIsCompatible(boolean isCompatible) {
        this.isCompatible = isCompatible;
    }
    public int getIdentifier() {
        return this.identifier;
    }
    public boolean getIsCompatible() {
        return this.isCompatible;
    }
    public Version(int identifier, boolean isCompatible) {
        this.identifier = identifier;
        this.isCompatible = isCompatible;
    }
}


class VersionTrackerManager {

    private List<Version> activeVersions;

    private List<Version>  nonCompatibleVersions;

    public VersionTrackerManager() {
        activeVersions = new ArrayList<>();
        nonCompatibleVersions = new ArrayList<>();
    }

    public List<Version> getActiveVersions () {
        return this.activeVersions;
    }

    public List<Version> getNonCompatibleVersions() {
        return this.nonCompatibleVersions;
    }

}


// class VersionTrackerClientException extends Exception {

//     public static final long serialVersionUID;

//     public VersionTrackerCleintException(String message, Throwable throwable) {
//         super(message, throwable);
//         serialVersionUID = 1;

//     }
// }



class AddVersionActivity {

    public void addNewVersion( final int version,  final boolean isCompatible,  final VersionTrackerManager versionTrackerManager) {

        validateInput(version, isCompatible);

        if(!isCompatible) {
            versionTrackerManager.getNonCompatibleVersions().add(new Version(version, isCompatible));
        }

    }

    private void validateInput(final int version, final boolean isCOmpatible) {
        if(version<0) {
            throw new RuntimeException("Please check the arguments provided!!");
        }

    }
}


class CheckCompatibilityActivity {
    public boolean isCompatible( final int srcVer,  final int targetVer, final VersionTrackerManager versionTrackerManager) {

        validateInput(srcVer, targetVer);

        int indexSrc = CheckCompatibiltyUtils.getIndexInNonCompatibleList(srcVer, versionTrackerManager);
        int indexTarget = CheckCompatibiltyUtils.getIndexInNonCompatibleList(targetVer, versionTrackerManager);

        System.out.println(indexSrc);
        System.out.println(indexTarget);


        if(indexSrc == indexTarget) {
            return true;
        }
        return false;

    }




    private void validateInput(final int srcVer, final int targetVer) {
        if(srcVer<0 || targetVer<0) {
            throw new RuntimeException("Please check the arguments provided!!");
        }
    }
}

class CheckCompatibiltyUtils {
    public static int getIndexInNonCompatibleList(int version, final VersionTrackerManager versionTrackerManager) {

        List<Version> nonCompatibleVersions = versionTrackerManager.getNonCompatibleVersions();

        int start_ = 0, end_ = nonCompatibleVersions.size(), mid = 0;

        while(start_ < end_) {


            mid = (start_+end_)/2;

            if(nonCompatibleVersions.get(mid).getIdentifier() > version) {
                end_ = mid-1;
            } else {
                start_ = start_+1;
            }
        }

        return start_;

    }
}



// Main class should be named 'Solution'
class Solution {

    final static AddVersionActivity addVersionActivity = new AddVersionActivity();
    final static CheckCompatibilityActivity checkCompatibilityVersion = new CheckCompatibilityActivity();

    public static void main(String[] args) {


        final VersionTrackerManager versionTrackerManager = new VersionTrackerManager();




        //         VersionCompatibilityManagement versions = new VersionCompatibilityManagement();
        addVersionActivity.addNewVersion(1, false, versionTrackerManager);
        addVersionActivity.addNewVersion(2, true, versionTrackerManager);
        addVersionActivity.addNewVersion(3, true, versionTrackerManager);
        addVersionActivity.addNewVersion(4, false, versionTrackerManager);
        addVersionActivity.addNewVersion(5, true, versionTrackerManager);
        addVersionActivity.addNewVersion(6, true, versionTrackerManager);
        // assert(versions.isCompatible(1, 3) == true);
        // assert(versions.isCompatible(3, 5) == false);
        // assert(versions.isCompatible(4, 2) == false); // downgrade
        // assert(versions.isCompatible(3, 3) == true); // upgrade to itself, always compatible

        // addVersionActivity.addNewVersion(1, true, versionTrackerManager);
        // addVersionActivity.addNewVersion(2, true, versionTrackerManager);


        System.out.println(checkCompatibilityVersion.isCompatible(3, 1 , versionTrackerManager));





        // while(1) {

        //     Scanner sc = new Scanner();

        //     int input = sc.nextInt();

        //     switch(input) {



        //         case 1 :

        //     }

        // }


        System.out.println("Hello, World");
    }
}


// Here is one test case:
// 1 -> 2 -> 3 -> 4 -> 5 -> 6
//    T    T    F    T    T

//         VersionCompatibilityManagement versions = new VersionCompatibilityManagement();
//         versions.addNewVersion(1, false);
//         versions.addNewVersion(2, true);
//         versions.addNewVersion(3, true);
//         versions.addNewVersion(4, false);
//         versions.addNewVersion(5, true);
//         versions.addNewVersion(6, true);
//         assert(versions.isCompatible(1, 3) == true);
//         assert(versions.isCompatible(3, 5) == false);
//         assert(versions.isCompatible(4, 2) == false); // downgrade
//         assert(versions.isCompatible(3, 3) == true); // upgrade to itself, always compatible