@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    // POST Create a new package
    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package package) {
        Package newPackage = packageService.createPackage(package);
        return new ResponseEntity<>(newPackage, HttpStatus.CREATED);
    }

    // GET All packages
    @GetMapping
    public List<Package> getAllPackages() {
        return packageService.getAllPackages();
    }

    // GET a package by ID
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Package package = packageService.getPackageById(id);
        return new ResponseEntity<>(package, HttpStatus.OK);
    }

    // PUT Update a package
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody Package packageDetails) {
        Package updatedPackage = packageService.updatePackage(id, packageDetails);
        return new ResponseEntity<>(updatedPackage, HttpStatus.OK);
    }

    // DELETE a package
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
