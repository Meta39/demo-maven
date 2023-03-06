package com.fu.demo.entity.vo;

import com.fu.demo.entity.User;
import com.fu.demo.entity.UserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO extends User implements Serializable {

    private static final long serialVersionUID = -6806735708706984383L;

    private List<UserDetails> userDetailsList;
}
