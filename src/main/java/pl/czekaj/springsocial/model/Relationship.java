package pl.czekaj.springsocial.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.czekaj.springsocial.model.modelHelper.RelationshipSerializer;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonSerialize(using = RelationshipSerializer.class)
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;

    private Long fromUserId;

    private Long toUserId;

}
