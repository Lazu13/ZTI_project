package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.dto.user.UserInsertDto;
import pl.edu.agh.zti.dto.user.UserReturnDto;
import pl.edu.agh.zti.model.EmptyJsonResponse;
import pl.edu.agh.zti.service.UserService;

/**
 * Rest controller with request mapping of "/users"
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor with autowired argument
     *
     * @param userService service processing user specific actions
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method gathering current user
     *
     * @return current user
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserReturnDto get(
    ) {
        return userService.get();
    }

    /**
     * Method creating new user in database
     *
     * @param dto user data
     * @return empty json reponse
     */
    @PostMapping
    public ResponseEntity save(@RequestBody final UserInsertDto dto) {
        userService.save(dto);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method gathering users by regex
     *
     * @param page number of page
     * @param size size number
     * @param username regex of username
     * @return page of [[UserReturnDto]]
     */
    @GetMapping("/like/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserReturnDto> getLike(
            @RequestParam int page,
            @RequestParam int size,
            @PathVariable String username
    ) {
        return userService.getByUsernameLike(username, page, size);
    }

    /**
     * Method deleting user from database
     * It requires ADMIN role
     *
     * @param id id of user
     * @return empty json response
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable final Long id) {
        userService.delete(id);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
