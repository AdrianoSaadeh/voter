package br.edu.ulbra.election.voter.service;

import br.edu.ulbra.election.voter.exception.GenericOutputException;
import br.edu.ulbra.election.voter.input.v1.VoterInput;
import br.edu.ulbra.election.voter.model.Voter;
import br.edu.ulbra.election.voter.output.v1.GenericOutput;
import br.edu.ulbra.election.voter.output.v1.VoterOutput;
import br.edu.ulbra.election.voter.repository.VoterRepository;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class VoterService {

	private final VoterRepository voterRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private static final String MESSAGE_INVALID_ID = "Invalid id";
	private static final String MESSAGE_VOTER_NOT_FOUND = "Voter not found";

	@Autowired
	public VoterService(VoterRepository voterRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.voterRepository = voterRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public List<VoterOutput> getAll() {
		Type voterOutputListType = new TypeToken<List<VoterOutput>>() {
		}.getType();
		return modelMapper.map(voterRepository.findAll(), voterOutputListType);
	}

	// ajuste para MD5
	// public VoterOutput create(VoterInput voterInput) {
	public VoterOutput create(VoterInput voterInput) throws NoSuchAlgorithmException {
		validateInput(voterInput, false);
		Voter voter = modelMapper.map(voterInput, Voter.class);
		voter.setPassword(passwordEncoder.encode(voter.getPassword()));
		voter = voterRepository.save(voter);
		return modelMapper.map(voter, VoterOutput.class);
	}

	public VoterOutput getById(Long voterId) {
		if (voterId == null) {
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}

		Voter voter = voterRepository.findById(voterId).orElse(null);
		if (voter == null) {
			throw new GenericOutputException(MESSAGE_VOTER_NOT_FOUND);
		}

		return modelMapper.map(voter, VoterOutput.class);
	}

	// ajuste para MD5
	// public VoterOutput update(Long voterId, VoterInput voterInput) {
	public VoterOutput update(Long voterId, VoterInput voterInput) throws NoSuchAlgorithmException {

		if (voterId == null) {
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}
		validateInput(voterInput, true);

		Voter voter = voterRepository.findById(voterId).orElse(null);
		if (voter == null) {
			throw new GenericOutputException(MESSAGE_VOTER_NOT_FOUND);
		}

		voter.setEmail(voterInput.getEmail());
		voter.setName(voterInput.getName());
		if (!StringUtils.isBlank(voterInput.getPassword())) {
			voter.setPassword(passwordEncoder.encode(voterInput.getPassword()));
		}
		voter = voterRepository.save(voter);
		return modelMapper.map(voter, VoterOutput.class);
	}

	public GenericOutput delete(Long voterId) {
		if (voterId == null) {
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}

		Voter voter = voterRepository.findById(voterId).orElse(null);
		if (voter == null) {
			throw new GenericOutputException(MESSAGE_VOTER_NOT_FOUND);
		}

		voterRepository.delete(voter);

		return new GenericOutput("Voter deleted");
	}

	// ajuste para MD5 e para validar o email
	// private void validateInput(VoterInput voterInput, boolean isUpdate){
	private void validateInput(VoterInput voterInput, boolean isUpdate) throws NoSuchAlgorithmException {

		if (StringUtils.isBlank(voterInput.getEmail()) || !validateEmail(voterInput.getEmail())) {
			throw new GenericOutputException("Invalid email");
		}
		if (StringUtils.isBlank(voterInput.getName()) || voterInput.getName().length() < 5
				|| voterInput.getName().split(" ").length < 2) {
			throw new GenericOutputException("Invalid name");
		}

		// pegar nome e no mínimo 1 sobrenome
		if (voterInput.getName().indexOf(" ") == -1) {
			throw new GenericOutputException("Your name must contain a last name");
		}

		if (!StringUtils.isBlank(voterInput.getPassword())) {
			if (!voterInput.getPassword().equals(voterInput.getPasswordConfirm())) {
				throw new GenericOutputException("Passwords doesn't match");
			}
		} else {
			if (!isUpdate) {
				throw new GenericOutputException("Password doesn't match");
			}
		}
	}

	public boolean validateEmail(String email) {
		Type partyOutputListType = new TypeToken<List<Voter>>() {
		}.getType();

		List<Voter> parties = modelMapper.map(voterRepository.findAll(), partyOutputListType);
		for (Voter party : parties)
			if (party.getEmail().equals(email))
				return false;

		return true;
	}

}
